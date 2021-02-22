/*
 * This file is part of project "BlockMacro"
 * "Sailor.java" made at 2021 - 2 - 22 from computer named "hrd201-9"
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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class Sailor implements PluginBase, Listener {
    @EventHandler
    public void onDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID uid = p.getUniqueId();
        Controller con = getController();
        if(e.isCancelled() || getPlugin().ignore(p) || con.stillInvaild(uid) || !con.isTrafficable(p.getWorld())) return;
        con.setTraffic(uid);
        if(con.isMacroAble(uid)) con.issue(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        getController().setMoved(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        UUID uid = p.getUniqueId();
        Controller con = getController();
        if(!con.stillInvaild(uid)) return;
        else {
            e.setCancelled(true);
            if(con.checkStr(uid, e.getMessage())) {
                con.setValid(uid, true);
                p.sendMessage(ChatColor.AQUA + "코드 일치");
            }
            else p.sendMessage(ChatColor.RED + "코드 불일치");
        }
    }
}
