/* *********************************************************************** *
 * project: org.matsim.*
 * PersonTreatmentRecorderTest.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
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

package playground.meisterk.phd.controler;

import junit.framework.TestCase;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.ScenarioImpl;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.basic.v01.IdImpl;

public class PersonTreatmentRecorderTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetRank() {
		
		Scenario sc = new ScenarioImpl() ;
		Population pop = sc.getPopulation() ;
		PopulationFactory pf = pop.getFactory() ;
		Person person = pf.createPerson(new IdImpl(1));
		for (double d : new double[]{180.0, 180.1, 180.5, 169.9}) {
			Plan plan = pf.createPlan();
			plan.setScore(d);
			person.addPlan(plan);
		}

		PersonTreatmentRecorder testee = new PersonTreatmentRecorder();
		
		for (int i=0; i<3; i++) {
			Plan plan = person.getPlans().get(i);
			plan.setSelected(true);
			int rank = testee.getRank(person);
			switch(i) {
			case 0:
				assertEquals("Wrong rank.", 2, rank);
				break;
			case 1:
				assertEquals("Wrong rank.", 1, rank);
				break;
			case 2:
				assertEquals("Wrong rank.", 0, rank);
				break;
			case 3:
				assertEquals("Wrong rank.", 3, rank);
				break;
			}
		}
		
	}
	
}
