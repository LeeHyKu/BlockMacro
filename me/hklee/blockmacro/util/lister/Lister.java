/*
 * This file is part of project "BlockMacro"
 * "Lister.java" made at 2021 - 2 - 22 from computer named "hrd201-9"
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

package me.hklee.blockmacro.util.lister;

import me.hklee.blockmacro.util.lister.runner.FindRunner;
import me.hklee.blockmacro.util.lister.runner.MapRunner;
import me.hklee.blockmacro.util.lister.runner.MaxRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lister {
    private static Lister lister = new Lister();
    public static Lister getInstance() { return lister; }

    public <A, R> ArrayList<R> map(List<A> source, MapRunner<A, R> runner) {
        ArrayList<R> r = new ArrayList<>();
        for(A now : source) {
            try { r.add(runner.apply(now)); }
            catch (Exception e) { }
        }
        return r;
    }

    public <A> ArrayList<A> filter(List<A> source, FindRunner<A> runner) {
        ArrayList<A> r = new ArrayList<>();
        for(A now : source) {
            try { if(runner.apply(now)) r.add(now); }
            catch (Exception e) { }
        }
        return r;
    }

    public <A> A find(List<A> source, FindRunner<A> runner) {
        for(A now : source) {
            try { if(runner.apply(now)) return now; }
            catch (Exception e) { }
        }
        return null;
    }

    public <A> boolean some(List<A> source, FindRunner<A> runner) {
        for(A now : source){
            try { if(runner.apply(now)) return true; }
            catch (Exception e){ }
        }
        return false;
    }

    public <A> A max(List<A> source, MaxRunner<A> runner) {
        Iterator<A> i = source.listIterator();
        A m = i.next();
        while (i.hasNext()){
            try {
                A n = i.next();
                if(runner.apply(n, m)) m = n;
            }
            catch (Exception e) { }
        }
        return m;
    }

    public <A> int maxindex(List<A> source, MaxRunner<A> runner){
        A m = source.get(0);
        int index = 0;
        for(int i = 1; i < source.size(); i++) {
            try {
                A n = source.get(i);
                if(runner.apply(n, m)) { m = n; index = i; }
            }
            catch (Exception e) { }
        }
        return index;
    }
}
