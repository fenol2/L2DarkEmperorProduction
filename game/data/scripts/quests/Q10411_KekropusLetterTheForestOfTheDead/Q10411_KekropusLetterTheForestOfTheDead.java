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
package quests.Q10411_KekropusLetterTheForestOfTheDead;

import com.l2jmobius.gameserver.model.Location;
import com.l2jmobius.gameserver.model.actor.L2Character;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.quest.QuestState;
import com.l2jmobius.gameserver.network.NpcStringId;
import com.l2jmobius.gameserver.network.serverpackets.ExShowScreenMessage;

import quests.LetterQuest;

/**
 * Kekropus' Letter: The Forest of the Dead (10411)
 * @author St3eT
 */
public final class Q10411_KekropusLetterTheForestOfTheDead extends LetterQuest
{
	// NPCs
	private static final int MATHIAS = 31340;
	private static final int HATUBA = 33849;
	private static final int INVISIBLE_NPC = 19543;
	// Items
	private static final int SOE_FOREST_OF_THE_DEAD = 37031; // Scroll of Escape: Forest of the Dead
	private static final int SOE_TOWN_OF_RUNE = 37118; // Scroll of Escape: Town of Rune
	private static final int EWA = 729; // Scroll: Enchant Weapon (A-grade)
	// Location
	private static final Location TELEPORT_LOC = new Location(42682, -47986, -792);
	// Misc
	private static final int MIN_LEVEL = 65;
	private static final int MAX_LEVEL = 69;
	
	public Q10411_KekropusLetterTheForestOfTheDead()
	{
		super(10411);
		addTalkId(MATHIAS, HATUBA);
		addSeeCreatureId(INVISIBLE_NPC);
		
		setIsErtheiaQuest(false);
		setLevel(MIN_LEVEL, MAX_LEVEL);
		setStartQuestSound("Npcdialog1.kekrops_quest_7");
		setStartLocation(SOE_TOWN_OF_RUNE, TELEPORT_LOC);
		registerQuestItems(SOE_TOWN_OF_RUNE, SOE_FOREST_OF_THE_DEAD);
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
			case "31340-02.html":
			{
				htmltext = event;
				break;
			}
			case "31340-03.html":
			{
				if (st.isCond(1))
				{
					takeItems(player, SOE_TOWN_OF_RUNE, -1);
					giveItems(player, SOE_FOREST_OF_THE_DEAD, 1);
					st.setCond(2, true);
					htmltext = event;
				}
				break;
			}
			case "33849-02.html":
			{
				if (st.isCond(2))
				{
					st.exitQuest(false, true);
					giveItems(player, EWA, 2);
					giveStoryQuestReward(npc, player);
					if (player.getLevel() >= MIN_LEVEL)
					{
						addExpAndSp(player, 942_690, 226);
					}
					showOnScreenMsg(player, NpcStringId.GROW_STRONGER_HERE_UNTIL_YOU_RECEIVE_THE_NEXT_LETTER_FROM_KEKROPUS_AT_LV_70, ExShowScreenMessage.TOP_CENTER, 6000);
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
		final QuestState st = getQuestState(player, false);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (st.isStarted())
		{
			if ((npc.getId() == MATHIAS) && st.isCond(1))
			{
				htmltext = "31340-01.html";
			}
			else if (st.isCond(2))
			{
				htmltext = npc.getId() == MATHIAS ? "31340-04.html" : "33849-01.html";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (creature.isPlayer())
		{
			final L2PcInstance player = creature.getActingPlayer();
			final QuestState st = getQuestState(player, false);
			
			if ((st != null) && st.isCond(2))
			{
				showOnScreenMsg(player, NpcStringId.FOREST_OF_THE_DEAD_IS_A_GOOD_HUNTING_ZONE_FOR_LV_65_OR_ABOVE, ExShowScreenMessage.TOP_CENTER, 6000);
			}
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public boolean canShowTutorialMark(L2PcInstance player)
	{
		return player.isMageClass();
	}
}