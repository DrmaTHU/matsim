package playground.jhackney.socialnetworks.algorithms;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import org.matsim.core.events.ActivityEndEvent;
import org.matsim.core.events.ActivityStartEvent;
import org.matsim.core.events.handler.ActivityEndEventHandler;
import org.matsim.core.events.handler.ActivityStartEventHandler;
import org.matsim.core.population.PersonImpl;
import org.matsim.core.population.PopulationImpl;

public class EventsMapStartEndTimes implements ActivityStartEventHandler, ActivityEndEventHandler {

	public LinkedHashMap<PersonImpl, ArrayList<ActivityStartEvent>> startMap = new LinkedHashMap<PersonImpl,ArrayList<ActivityStartEvent>>();
	public LinkedHashMap<PersonImpl, ArrayList<ActivityEndEvent>> endMap = new LinkedHashMap<PersonImpl,ArrayList<ActivityEndEvent>>();
	public double maxtime=0;
	private PopulationImpl plans;
	static final private Logger log = Logger.getLogger(EventsMapStartEndTimes.class);

	public EventsMapStartEndTimes(PopulationImpl plans) {
		super();
//		makeTimeWindows();
		this.plans=plans;
		log.info(" Looking through plans and mapping social interactions for scoring");
	}

	public void handleEvent(ActivityStartEvent event) {
		PersonImpl person = plans.getPersons().get(event.getPersonId());
		ArrayList<ActivityStartEvent> startList;
		if((startMap.get(person)==null)){
			startList=new ArrayList<ActivityStartEvent>();
		}else{
			startList=startMap.get(person);
		}
		startList.add(event);
		startMap.remove(person);
		startMap.put(person,startList);
		if(event.getTime()>=maxtime) maxtime=event.getTime();
	}

	public void reset(int iteration) {
		startMap.clear();
		endMap.clear();

	}

	public void handleEvent(ActivityEndEvent event) {
		PersonImpl person = plans.getPersons().get(event.getPersonId());
		ArrayList<ActivityEndEvent> endList;
		if((endMap.get(person)== null)){
			endList=new ArrayList<ActivityEndEvent>();
		}else{
			endList=endMap.get(person);
		}
		endList.add(event);
		endMap.remove(person);
		endMap.put(person,endList);
		if(event.getTime()>=maxtime) maxtime=event.getTime();
	}
	public double getMaxTime(){
		return maxtime;
	}
}
