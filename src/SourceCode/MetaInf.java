/*******************************************************************************
*   Project: LoL Assembler
*
*   File: MetaInf.java
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
public class MetaInf {

    // THIS VARIABLE MUST BE SET MANUALLY EACH TIME A NEW VERSION OF THE SOFTWARE IS RELEASED
    /////////////////////////////////////////////////////////////////////////////////////////
    double softwareVersion = 1.2;
    /////////////////////////////////////////////////////////////////////////////////////////

    String about;
    String license;

    public MetaInf () {
        about = "  -------------------------------------------------------------  \n" +
            "  ---------------------|||||||||||||||||||---------------------  \n" +
            "  ---------------------|| LOL ASSEMBLER ||---------------------  \n" +
            "  ---------------------|||||||||||||||||||---------------------  \n" +
            "                                                                 \n" +
            "  A powerful, customizable and free tool for strategic picks in  \n" +
            "         League of Leguends game (Owned by Riot Games)           \n" +
            "                                                                 \n" +
            "                    Version 1.2, January 2016                    \n" + 
            "  -------------------------------------------------------------  \n" +
            "                                                                 \n" +
            "          DEVELOPED AND OWNED BY Alberto Martin Cajal            \n" +
            "           Author: Alberto Martin Cajal, Spain, 2015             \n" +
            "              Email: amartin.glimpse23@gmail.com                 \n" +
            "                    Twitter: @amartin_g23                        \n" +
            "                                                                 \n" +
            "                                                                 \n" +
            "   Download links, information and other stuff in the official blog:   \n" +
            "           http://glimpse-23.blogspot.com.es/              \n" +
            "                                                                 \n" +
            "  *************************************************************  \n\n" +
            "LoL ASSEMBLER is a tool for strategic picks\n " +
            "in League of Legends(R), the most successful MOBA ever.\n" +
            "LoL ASSEMBLER helps you to build the perfect team \n" +
            "in a smart way, using counter-pick data from \n" + 
            "the community of players and your own customizable information. \n" + 
            "The tool is completely free, and can be used in all game modes, \n" + 
            "with a bunch of related features. Whether you are a newbie \n" + 
            "who needs a little impulse to start in the competitive way; \n" + 
            "an experienced player seeking for challenger tier; a trainer \n" + 
            "who wants a decision support system, or just a gamer\n" +
            "with passion for chess-like planning and strategies,\n"+
            "LoL ASSEMBLER will aim you in the fields of justice.  \n\n";  
        
        
        
        
        
        license = "  -------------------------------------------------------------  \n" +
            "  ---------------------|||||||||||||||||||---------------------  \n" +
            "  ---------------------|| LOL ASSEMBLER ||---------------------  \n" +
            "  ---------------------|||||||||||||||||||---------------------  \n" +
            "                                                                 \n" +
            "  A powerful, customizable and free tool for strategic picks in  \n" +
            "         League of Leguends game (Owned by Riot Games)           \n" +
            "                                                                 \n" +
            "                    Version 1.2, January 2016                    \n" + 
            "  -------------------------------------------------------------  \n" +
            "                                                                 \n" +
            "          DEVELOPED AND OWNED BY Alberto Martin Cajal            \n" +
            "           Author: Alberto Martin Cajal, Spain, 2015             \n" +
            "              Email: amartin.glimpse23@gmail.com                 \n" +
            "                    Twitter: @amartin_g23                        \n" +
            "                                                                 \n" +
            "                                                                 \n" +
            "   Download links, information and other stuff in the official blog:   \n" +
            "           http://glimpse-23.blogspot.com.es/              \n" +
            "                                                                 \n" +
            "  *************************************************************  \n" +
            " 						License: GNU GPL v3.0 					\n" +
			"\n" + 
			"   Copyright (C) 2018 Alberto Martin Cajal						\n" + 
			"\n" + 
			"   This file is part of LoL Assembler project.					\n" + 
			"\n" + 
			"   LoL Assembler is free software: you can redistribute it and/or modify\n" + 
			"   it under the terms of the GNU General Public License as published by\n" + 
			"   the Free Software Foundation, either version 3 of the License, or\n" + 
			"   (at your option) any later version.\n" + 
			"\n" + 
			"   LoL Assembler is distributed in the hope that it will be useful,\n" + 
			"   but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + 
			"   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + 
			"   GNU General Public License for more details.\n" + 
			"\n" + 
			"   You should have received a copy of the GNU General Public License\n" + 
			"   along with this program.  If not, see <http://www.gnu.org/licenses/>." +
            "                                                                 ";
    }

    public double getSV () {
        return this.softwareVersion;
    }

    public void setSV (double version) {
        this.softwareVersion = version;
    }

    public String getAbout () {
        return this.about;
    }

    public String getLicense () {
        return this.license;
    }
}
