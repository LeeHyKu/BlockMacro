/*
 * This file is part of project "BlockMacro"
 * "Controller.java" made at 2021 - 2 - 22 from computer named "hrd201-9"
 *
 * Copyright (C) 2021 Hyun-Ku Lee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.hklee.blockmacro;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Controller implements PluginBase {
    private Map<UUID, Date> traffic = new HashMap<>();
    public boolean isIn(UUID uid) { return traffic.containsKey(uid); }
    public void setTraffic(UUID uid) { if(!isIn(uid)) traffic.put(uid, new Date(new Date().getTime() + getConfiger().TIME)); }
    public void setMoved(UUID uid) { if(isIn(uid)) traffic.remove(uid); }
    public boolean isMacroAble(UUID uid) { return isIn(uid) && traffic.get(uid).before(new Date()); }
    public boolean isTrafficable(World world){ return getLister().some(getConfiger().DETECTWORLD, w -> w.equals(world)); }

    private Map<UUID, String> valid = new HashMap<>();
    private Map<UUID, BukkitTask> validtask = new HashMap<>();
    public void issue(Player p) {
        UUID uid = p.getUniqueId();
        setMoved(uid);
        String str = RandomStringUtils.randomAlphanumeric(7).toLowerCase(Locale.ROOT);
        valid.put(uid, str);
        Configer c = getConfiger();
        List<String> stl = c.getMessage(str);
        p.sendMessage(stl.toArray(new String[stl.size()]));
        p.sendTitle(c.getTitle(str), c.TITLE_SUB, c.TITLE_FADIN, c.TITLE_SHOW, c.TITLE_FADOUT);
        validtask.put(uid, getServer().getScheduler().runTaskLater(getPlugin(), () -> {
            if(stillInvaild(uid)) {
                setValid(uid, false);
                teleport(p);
                p.sendMessage(ChatColor.RED + "올바른 답을 입력하지 않아 강제 텔레포트 되었습니다.");
            }
        }, c.WAIT));
    }
    public boolean checkStr(UUID uid, String str) {
        if(!valid.containsKey(uid)) return true;
        return valid.get(uid).equalsIgnoreCase(str);
    }
    public boolean stillInvaild(UUID uid) { return valid.containsKey(uid); }
    public void setValid(UUID uid, boolean cancel){
        if(valid.containsKey(uid)) valid.remove(uid);
        if(validtask.containsKey(uid)) {
            BukkitTask t = validtask.remove(uid);
            if(cancel) {
                t.cancel();
                setMoved(uid);
            }
        }
    }
    private void teleport(Player p) { p.teleport(getConfiger().TELEPORT.clone()); }
}
