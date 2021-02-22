/*
 * This file is part of project "BlockMacro"
 * "Configer.java" made at 2021 - 2 - 22 from computer named "hrd201-9"
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

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Configer implements PluginBase {
    public final String TITLE_MAIN;
    public final String TITLE_SUB;
    public final int TITLE_FADIN;
    public final int TITLE_SHOW;
    public final int TITLE_FADOUT;

    public final List<String> MESSAGE;

    public final int TIME;
    public final int WAIT;

    public final List<World> DETECTWORLD;
    public final Location TELEPORT;

    public String getTitle(String code) { return TITLE_MAIN.replace("<code>", code); }
    public List<String> getMessage(String code) { return getLister().map(MESSAGE, s -> s.replace("<code>", code)); }

    public Configer(FileConfiguration c) {
        TITLE_MAIN = c.getString("titlemain");
        TITLE_SUB = c.getString("titlesub");
        TITLE_FADIN = c.getInt("titlefadin");
        TITLE_SHOW = c.getInt("titleshow");
        TITLE_FADOUT = c.getInt("titlefadout");

        MESSAGE = c.getStringList("message");

        TIME = c.getInt("time") * 1000;
        WAIT = c.getInt("wait") * 20;

        Server s = getServer();
        DETECTWORLD = getLister().map(c.getStringList("detectworld"), w -> s.getWorld(w));
        TELEPORT = new Location(s.getWorld(c.getString("teleport.world")), c.getDouble("teleport.x"), c.getDouble("teleport.y"), c.getDouble("teleport.z"));
    }
}
