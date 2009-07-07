/* *********************************************************************** *
 * project: org.matsim.*
 * PopulationCreation.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package playground.balmermi.census2000v2;

import org.matsim.core.basic.v01.IdImpl;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.facilities.ActivityFacilities;
import org.matsim.core.facilities.FacilitiesWriter;
import org.matsim.core.facilities.MatsimFacilitiesReader;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationWriter;
import org.matsim.knowledges.Knowledges;
import org.matsim.knowledges.KnowledgesImpl;
import org.matsim.world.Layer;
import org.matsim.world.MatsimWorldReader;
import org.matsim.world.World;
import org.matsim.world.WorldWriter;
import org.matsim.world.algorithms.WorldCheck;
import org.matsim.world.algorithms.WorldMappingInfo;

import playground.balmermi.census2000.data.Municipalities;
import playground.balmermi.census2000v2.data.Households;
import playground.balmermi.census2000v2.modules.HouseholdsCreateFromCensus2000;
import playground.balmermi.census2000v2.modules.PlansCreateFromCensus2000;
import playground.balmermi.census2000v2.modules.WorldFacilityZoneMapping;
import playground.balmermi.census2000v2.modules.WorldWriteFacilityZoneMapping;

public class PopulationCreation {

	//////////////////////////////////////////////////////////////////////
	// createPopulation()
	//////////////////////////////////////////////////////////////////////

	public static void createPopulation(Config config) {

		System.out.println("MATSim-DB: create Population based on census2000 data.");

		World world = Gbl.createWorld();
		
		//////////////////////////////////////////////////////////////////////

		System.out.println("  extracting input directory... ");
		String indir = config.facilities().getInputFile();
		indir = indir.substring(0,indir.lastIndexOf("/"));
		System.out.println("    "+indir);
		System.out.println("  done.");

		System.out.println("  extracting output directory... ");
		String outdir = config.facilities().getOutputFile();
		outdir = outdir.substring(0,outdir.lastIndexOf("/"));
		System.out.println("    "+outdir);
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  reading world xml file...");
		final MatsimWorldReader worldReader = new MatsimWorldReader(world);
		worldReader.readFile(config.world().getInputFile());
		System.out.println("  done.");

		System.out.println("  reading facilities xml file...");
		ActivityFacilities facilities = (ActivityFacilities)world.createLayer(ActivityFacilities.LAYER_TYPE, null);
		new MatsimFacilitiesReader(facilities).readFile(config.facilities().getInputFile());
		world.complete();
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  parsing additional municipality information... ");
		Municipalities municipalities = new Municipalities(indir+"/gg25_2001_infos.txt");
		Layer municipalityLayer = world.getLayer(new IdImpl(Municipalities.MUNICIPALITY));
		municipalities.parse(municipalityLayer);
		System.out.println("  done.");

		System.out.println("  creating Households object... ");
		Households households = new Households(municipalities);
		System.out.println("  done.");

		System.out.println("  creating plans object...");
		PopulationImpl plans = new PopulationImpl();
		Knowledges knowledges =  new KnowledgesImpl();
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  running world modules... ");
		new WorldCheck().run(world);
		new WorldMappingInfo().run(world);
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  running household modules... ");
		new HouseholdsCreateFromCensus2000(indir+"/ETHZ_Pers.tab",facilities,municipalities).run(households, municipalityLayer);
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  running world modules... ");
		new WorldFacilityZoneMapping(households).run(world);
		new WorldCheck().run(world);
		new WorldMappingInfo().run(world);
		new WorldWriteFacilityZoneMapping(outdir+"/output_f2z_mapping.txt").run(world);
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  running plans modules... ");
		new PlansCreateFromCensus2000(indir+"/ETHZ_Pers.tab",households,facilities, knowledges).run(plans, municipalityLayer);
//		new PlansWriteCustomAttributes(outdir+"/output_persons.txt").run(plans);
		System.out.println("  done.");

		//////////////////////////////////////////////////////////////////////

		System.out.println("  writing plans xml file... ");
		PopulationWriter plans_writer = new PopulationWriter(plans);
		plans_writer.write();
		System.out.println("  done.");

		System.out.println("  writing households txt file... ");
		households.writeTable(outdir+"/output_households.txt");
		System.out.println("  done.");

		System.out.println("  writing facilities xml file... ");
		FacilitiesWriter fac_writer = new FacilitiesWriter(facilities);
		fac_writer.write();
		System.out.println("  done.");

		System.out.println("  writing world xml file... ");
		WorldWriter world_writer = new WorldWriter(world);
		world_writer.write();
		System.out.println("  done.");

		System.out.println("  writing config xml file... ");
		ConfigWriter config_writer = new ConfigWriter(config);
		config_writer.write();
		System.out.println("  done.");

		System.out.println("done.");
		System.out.println();
	}

	//////////////////////////////////////////////////////////////////////
	// main
	//////////////////////////////////////////////////////////////////////

	public static void main(final String[] args) {

		Gbl.startMeasurement();

		Config config = Gbl.createConfig(args);

		createPopulation(config);

		Gbl.printElapsedTime();
	}
}
