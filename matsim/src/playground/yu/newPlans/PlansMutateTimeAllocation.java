/* *********************************************************************** *
 * project: org.matsim.*
 * PlansMutateTimeAllocation.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
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

/**
 * 
 */
package playground.yu.newPlans;

import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.network.NetworkLayer;
import org.matsim.core.population.MatsimPopulationReader;
import org.matsim.core.population.PersonImpl;
import org.matsim.core.population.PlanImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationImpl;
import org.matsim.core.population.PopulationWriter;
import org.matsim.population.algorithms.PlanMutateTimeAllocation;

/**
 * @author yu
 * 
 */
public class PlansMutateTimeAllocation {

	public static void main(String[] args) {

		NetworkLayer network = new NetworkLayer();
		new MatsimNetworkReader(network)
				.readFile("D:/fromNB04/wm/Toronto/toronto/networks/changedNetworkWithManeuvers/network.xml");

		PopulationImpl population = new PopulationImpl();
		new MatsimPopulationReader(population, network)
				.readFile("D:/fromNB04/wm/Toronto/toronto/plans/xy/plans.xml.gz");

		PlanMutateTimeAllocation pmta = new PlanMutateTimeAllocation(1800,
				MatsimRandom.getLocalInstance());

		for (PersonImpl person : population.getPersons().values())
			for (PlanImpl plan : person.getPlans())
				pmta.run(plan);

		new PopulationWriter(population,
				"D:/fromNB04/wm/Toronto/toronto/plans/xy/plansBlurred.xml.gz")
				.write();

		System.out.println("done.");
	}
}
