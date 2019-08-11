/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Flags.java
*
*   Description: @TODO
*
*   Notes: N/A
*
*   Contact: Alberto Martin Cajal, amartin.glimpse23<AT>gmail.com
*
*   URL: https://github.com/amcajal/lol_assembler
*
*   License: GNU GPL v3.0
*
*   Copyright (C) 2018 Alberto Martin Cajal
*
*   This file is part of LoL Assembler project.
*
*   LoL Assembler is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   LoL Assembler is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*******************************************************************************/


package SourceCode;

/**
 *
 * @author Alberto
 */

public enum Flags {

    MAP, LIST, STRONG, WEAK, EVEN, WELL, TIPS, HTML, HOT, LOVED, HATED, COMBOS, // FileTypes
    PSTRONG, PWEAK, PEVEN, PWELL, // Personal FileTypes
    NORMAL, RANKED, VS3, VS5, // Game types (mode and number of players)
    ADD, DELETE, // Operations
    ALLYSTART, ENEMYSTART, // Pick and Ban orders
    ABOUT, LICENSE, // Special windows
    top, jungle, mid, adc, support // Positions 

    /* The file types are processed in different ways, depends on
            the class wich uses them. I.E: FileWorker uses "Strong" to
            know that the "strong against" data must be gathered. However,
            Information class uses "Strong" to know that the map wich gonna
            be loaded, contains the "strong against" data
     */

     // HTML type is only used to gather the "weak", "strong" etc. information at the same time
     // HTML type appear only in fileWorker.read() to trigger the intended IF branch




}
