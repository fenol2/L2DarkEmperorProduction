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
package custom.CustomNpcs.Beta;

import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.model.holders.SkillHolder;
import com.l2jmobius.gameserver.model.skills.Skill;
import com.l2jmobius.gameserver.model.skills.SkillCaster;

import ai.AbstractNpcAI;

/**
 * Beta NPC
 * @author Lucas Gutierrez
 */
public final class Beta extends AbstractNpcAI
{
	// NPC
	private static final int BETA_NPC = 607;
	private static final int ADENA = 57;
	private static final int GOLD = 4356;

	private Beta()
	{
		addStartNpc(BETA_NPC);
		addTalkId(BETA_NPC);
		addFirstTalkId(BETA_NPC);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		
		switch (event)
		{
			case "onem":
			{
				player.addExpAndSp(1000000L, 0L);
				break;
			}
			case "onehm":
			{
				player.addExpAndSp(100000000L, 0L);
				break;
			}
			case "tenb":
			{
				player.addExpAndSp(10000000000L, 0L);
				break;
			}
			case "onet":
			{
				player.addExpAndSp(1000000000000L, 0L);
				break;
			}
			case "oneht":
			{
				player.addExpAndSp(100000000000000L, 0L);
				break;
			}
			case "sp":
			{
				player.addExpAndSp(0L, 50000000000L);
				break;
			}
			case "adena":
			{
				giveItems(player, ADENA, 100000000);
				break;
			}
			case "gold":
			{
				giveItems(player, GOLD, 100000);
				break;
			}
		}

		htmltext = npc.getId() + ".html";

		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Beta();
	}
}