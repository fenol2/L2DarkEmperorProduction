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
package quests.Q00110_ToThePrimevalIsle;

import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.quest.Quest;
import com.l2jmobius.gameserver.model.quest.QuestState;
import com.l2jmobius.gameserver.model.quest.State;

/**
 * To the Primeval Isle (110)
 * @author Adry_85, Gladicek
 */
public class Q00110_ToThePrimevalIsle extends Quest
{
	// NPCs
	private static final int ANTON = 31338;
	private static final int MARQUEZ = 32113;
	// Item
	private static final int ANCIENT_BOOK = 8777;
	// Misc
	private static final int MIN_LEVEL = 75;
	
	public Q00110_ToThePrimevalIsle()
	{
		super(110);
		addStartNpc(ANTON);
		addTalkId(ANTON, MARQUEZ);
		addCondMinLevel(MIN_LEVEL, "");
		registerQuestItems(ANCIENT_BOOK);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = null;
		
		switch (event)
		{
			case "31338-03.htm":
			case "31338-04.htm":
			case "32113-02.html":
			case "32113-03.html":
			{
				htmltext = event;
				break;
			}
			case "31338-05.html":
			{
				giveItems(player, ANCIENT_BOOK, 1);
				st.startQuest();
				break;
			}
			case "32113-04.html":
			case "32113-05.html":
			{
				if (st.isCond(1))
				{
					if ((player.getLevel() >= MIN_LEVEL))
					{
						giveAdena(player, 189208, true);
						addExpAndSp(player, 887732, 213);
						st.exitQuest(false, true);
					}
					else
					{
						htmltext = getNoQuestLevelRewardMsg(player);
					}
					break;
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
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				if (npc.getId() == ANTON)
				{
					htmltext = "31338-01.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if (npc.getId() == ANTON)
				{
					if (st.isCond(1))
					{
						htmltext = "32113-06.html";
					}
				}
				else if (npc.getId() == MARQUEZ)
				{
					if (st.isCond(1))
					{
						htmltext = "32113-01.html";
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
}
