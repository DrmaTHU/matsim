package playground.gregor.demandmodeling;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jgap.Population;
import org.matsim.api.basic.v01.Coord;
import org.matsim.api.basic.v01.Id;
import org.matsim.api.basic.v01.TransportMode;
import org.matsim.core.api.experimental.Scenario;
import org.matsim.core.api.experimental.ScenarioImpl;
import org.matsim.core.api.experimental.population.PopulationBuilder;
import org.matsim.core.basic.v01.IdImpl;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.network.NetworkLayer;
import org.matsim.core.population.ActivityImpl;
import org.matsim.core.population.PersonImpl;
import org.matsim.core.population.PlanImpl;
import org.matsim.core.population.PlanImpl;
import org.matsim.core.population.PopulationWriter;
import org.matsim.core.utils.geometry.CoordImpl;
import org.matsim.core.utils.misc.StringUtils;


public class AsciiToPlan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String INPUT_FILE = "../../inputs/networks/m3.dat";
		final String NET_FILE="../../inputs/networks/padang_net_v20080618.xml";
		final String OUTPUT_FILE="./tmp/population.xml.gz";
		final int MAX_SIZE = 13;
		final char TRENNER = ' ';
		
		BufferedReader in = null;
		String spalten_v[] = new String[MAX_SIZE*2];
		String spalten[] = new String[MAX_SIZE];
		int zaehler=0;
		Double homex,homey,awayx,awayy,start,dur,actType,mode;
		char zeichen;
		int wert=0;
		StringBuffer kette = new StringBuffer();
		
		
		//ffne ASCII Input File
		try {
			in = new BufferedReader(new FileReader(new File(INPUT_FILE)));
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Einlesen");
			e.printStackTrace();
			
		}
		System.out.println("ffnen der Datei erfolgreich!");
		//lese 1. Zeile ein
		String zeile = null;
		try {
			zeile = in.readLine() + " ";
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen der 1. Zeile");
			e.printStackTrace();
		}
		
		//ffnen des Szenarios
		Scenario sc = new ScenarioImpl();
		NetworkLayer net = sc.getNetwork();
		new MatsimNetworkReader(net).readFile(NET_FILE);
		org.matsim.core.population.PopulationImpl pop = sc.getPopulation();
		PopulationBuilder pb = pop.getPopulationBuilder();
		
		//Starte die Verarbeitung
		do{
			int j=0;
			zaehler++;
			Id id =new IdImpl(zaehler); 
			PersonImpl person = pb.createPerson(id);
			PlanImpl plan = pb.createPlan(person);
			person.addPlan(plan);
			//Lese wichtige Daten ein
//			spalten_v = StringUtils.explode(zeile, TRENNER);
			for (int i =0; i<zeile.length();i++){
				zeichen = zeile.charAt(i);
				if (zeichen!=' '){
					kette.append(zeichen);
					if (i<zeile.length()-1){
						if (zeile.charAt(i+1) == ' '){
							spalten[j] = kette.toString();
							j++;
							kette.delete(0, kette.length());
						}
					}
					else {
						spalten[j] = kette.toString();
					}
				}
			}
			if (Double.parseDouble(spalten[7])<0) spalten[7]="0";
//			for (int i = 0; i<MAX_SIZE;i++){
//				System.out.println(spalten[i]);	
//			}
			
//			for (int i = 0; i<(MAX_SIZE*2+1); i++){
//				if ((i % 2 == 0) && (i>0))
//					spalten[(i/2)-1]=spalten_v[i];
//			}
			//Verarbeite Daten
			actType=Double.parseDouble(spalten[5]);
			mode=Double.parseDouble(spalten[6]);
			start=Double.parseDouble(spalten[7]);
			dur=Double.parseDouble(spalten[8]);
			homex=Double.parseDouble(spalten[9]);
			homey=Double.parseDouble(spalten[10]);
			awayx=Double.parseDouble(spalten[11]);
			awayy=Double.parseDouble(spalten[12]);
			
			//Erzeuge Homeactivity
			Coord coord1 = new CoordImpl(homex,homey);
			String type = "h";
			ActivityImpl home1 = plan.createActivity(type, coord1);
			home1.setEndTime(start);
			
			plan.addLeg(pb.createLeg(TransportMode.car));
			
			//Erzeuge Workactivity
			Coord coord2 = new CoordImpl(awayx,awayy);
			
			if (actType == 1) {
				type = "w";
			} else	if (actType == 2) {
				type = "edu";
			}else	if (actType == 3) {
				type = "r";
			}else	if (actType == 4) {
				type = "soc";
			}
			ActivityImpl work = plan.createActivity(type, coord2);
			work.setStartTime(start);
			work.setEndTime(start + dur);
			
			plan.addLeg(pb.createLeg(TransportMode.car));
			
			//Erzeuge 2. Homeactivity
			type = "h";
			ActivityImpl home2 = plan.createActivity(type, coord1);
			home2.setStartTime(start + dur);
			
			try {
				zeile=in.readLine();
			} catch (IOException e) {
				System.out.println("Fehler beim Einlesen einer Zeile");
				e.printStackTrace();
			}
//			System.out.println(zaehler +". Person eingelesen");
			pop.addPerson(person);
		} while(zeile!=null);
		
		//Alles fertig --- Ergebnis ausgeben.
		new PopulationWriter(pop,OUTPUT_FILE).write();
		System.out.println("Ausgabe erzeugt");
		
		
	}

}
