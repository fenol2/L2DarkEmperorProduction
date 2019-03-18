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
package custom.CustomNpcs.GmShop;

import ai.AbstractNpcAI;
import com.l2jmobius.gameserver.handler.CommunityBoardHandler;
import com.l2jmobius.gameserver.model.actor.L2Npc;
import com.l2jmobius.gameserver.model.actor.instance.L2PcInstance;
import com.l2jmobius.gameserver.cache.HtmCache;

/**
 * GmShop NPC.
 * @author Lucas Gutierrez
 */
public final class GmShop extends AbstractNpcAI
{
	// NPC
	private static final int LEONA_BLACKBIRD = 600;
	// PATH
	private static final String NAVIGATION_PATH = "data/html/CommunityBoard/Custom/navigation.html";

	private GmShop()
	{
		addStartNpc(LEONA_BLACKBIRD);
		addTalkId(LEONA_BLACKBIRD);
		addFirstTalkId(LEONA_BLACKBIRD);
	}

	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String returnHtml = null;
		final String navigation = HtmCache.getInstance().getHtm(player, NAVIGATION_PATH);
		final String customPath = "Custom/";
		
		returnHtml = HtmCache.getInstance().getHtm(player, "data/html/CommunityBoard/" + customPath + "merchant/main.html");
		
		if (returnHtml != null)
		{
			returnHtml = returnHtml.replace("%navigation%", navigation);
			CommunityBoardHandler.separateAndSend(returnHtml, player);
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new GmShop();
	}
}