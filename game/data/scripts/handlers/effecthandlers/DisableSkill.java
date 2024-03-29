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
package handlers.effecthandlers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.l2jmobius.gameserver.model.StatsSet;
import com.l2jmobius.gameserver.model.actor.L2Character;
import com.l2jmobius.gameserver.model.effects.AbstractEffect;
import com.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Ofelin
 */
public class DisableSkill extends AbstractEffect
{
	private final Set<Integer> disableSkills;
	private Skill knownSKill;
	
	public DisableSkill(StatsSet params)
	{
		String disable = params.getString("disable");
		if ((disable != null) && !disable.isEmpty())
		{
			disableSkills = new HashSet<>();
			for (String slot : disable.split(";"))
			{
				disableSkills.add(Integer.parseInt(slot));
			}
		}
		else
		{
			disableSkills = Collections.<Integer> emptySet();
		}
	}
	
	@Override
	public void onStart(L2Character effector, L2Character effected, Skill skill)
	{
		for (int disableSkillId : disableSkills)
		{
			knownSKill = effected.getKnownSkill(disableSkillId);
			if (knownSKill != null)
			{
				effected.disableSkill(knownSKill, 0);
				
			}
		}
	}
	
	@Override
	public void onExit(L2Character effector, L2Character effected, Skill skill)
	{
		for (int enableSkillId : disableSkills)
		{
			knownSKill = effected.getKnownSkill(enableSkillId);
			if (knownSKill != null)
			{
				effected.enableSkill(knownSKill);
			}
		}
	}
}
