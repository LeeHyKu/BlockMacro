/*
 * This file is part of project "BlockMacro"
 * "Blockmacro.java" made at 2021 - 2 - 22 from computer named "hrd201-9"
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

import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blockmacro extends JavaPlugin {
    private static Blockmacro instance = null;
    public static Blockmacro getInstance() { return instance; }

    private Configer configer;
    private Controller controller;
    private Sailor sailor;
    public Configer getConfiger() { return configer; }
    public Controller getController() { return controller; }
    public Sailor getSailor() { return sailor; }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        configer = new Configer(getConfig());
        controller = new Controller();
        sailor = new Sailor();

        getServer().getPluginManager().registerEvents(sailor, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }

    public boolean ignore(Permissible p) { return p.hasPermission("blockmacro.ignore"); }
}
