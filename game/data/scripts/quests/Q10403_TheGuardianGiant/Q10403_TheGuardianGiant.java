/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10403_TheGuardianGiant;

import com.l2jmobius.gameserver.enums.ChatType;
import com.l2jmobius.gameserver.enums.Race;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.quest.Quest;
import com.l2jmobius.gameserver.model.quest.QuestState;
import com.l2jmobius.gameserver.model.quest.State;
import com.l2jmobius.gameserver.network.NpcStringId;

import quests.Q10402_NowhereToTurn.Q10402_NowhereToTurn;

/**
 * The Guardian Giant (10403)
 * @author St3eT
 */
public final class Q10403_TheGuardianGiant extends Quest
{
	// NPCs
	private static final int NOVIAN = 33866;
	private static final int AKUM = 27504; // Guardian Giant Akum
	private static final int[] MONSTERS =
	{
		20650, // Kranrot
		20648, // Paliote
		20647, // Yintzu
		20649, // Hamrut
	};
	// Items
	private static final int FRAGMENT = 36713; // Guardian Giant's Nucleus Fragment
	private static final int EAB = 948; // Scroll: Enchant Armor (B-grade)
	// Misc
	private static final int MIN_LEVEL = 58;
	private static final int MAX_LEVEL = 61;
	
	public Q10403_TheGuardianGiant()
	{
		super(10403);
		addStartNpc(NOVIAN);
		addTalkId(NOVIAN);
		addKillId(MONSTERS);
		addKillId(AKUM);
		registerQuestItems(FRAGMENT);
		addCondNotRace(Race.ERTHEIA, "33866-08.html");
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33866-09.htm");
		addCondCompletedQuest(Q10402_NowhereToTurn.class.getSimpleName(), "33866-09.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "33866-02.htm":
			case "33866-03.htm":
			{
				htmltext = event;
				break;
			}
			case "33866-04.htm":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "33866-07.html":
			{
				if (st.isCond(3))
				{
					st.exitQuest(false, true);
					giveItems(player, EAB, 5);
					giveStoryQuestReward(npc, player);
					if (player.getLevel() >= MIN_LEVEL)
					{
						addExpAndSp(player, 6_579_090, 1_578);
					}
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = "33866-01.htm";
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					case 2:
					{
						htmltext = "33866-05.html";
						break;
					}
					case 3:
					{
						htmltext = "33866-06.html";
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = getQuestState(killer, false);
		
		if ((st != null) && st.isStarted())
		{
			if (st.isCond(2) && (npc.getId() == AKUM))
			{
				st.setCond(3, true);
				takeItems(killer, FRAGMENT, -1);
				npc.broadcastSay(ChatType.NPC_GENERAL, NpcStringId.YOU_WITH_THE_POWER_OF_THE_GODS_CEASE_YOUR_MASQUERADING_AS_OUR_MASTERS_OR_ELSE);
			}
			else if (st.isCond(1))
			{
				if (giveItemRandomly(killer, FRAGMENT, 1, 50, 1, true))
				{
					st.setCond(2, true);
					final L2Npc akum = addSpawn(AKUM, npc);
					akum.broadcastSay(ChatType.NPC_GENERAL, NpcStringId.WHO_IS_IT_THAT_THREATENS_US_YOU_WITH_THE_POWER_OF_THE_GODS_WHY_DO_YOU_COVET_OUR_POWERS);
					addAttackPlayerDesire(akum, killer);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
}