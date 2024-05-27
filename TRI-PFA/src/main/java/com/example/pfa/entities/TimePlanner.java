package com.example.pfa.entities;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import lombok.Data;

@PlanningSolution
@Data
public class TimePlanner {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeslotList;
 
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Salle> salleList;

    @PlanningEntityCollectionProperty
    private List<Seance> seanceList;

    @PlanningScore
    private HardSoftScore score;

    public TimePlanner() {
    }

    public TimePlanner(List<TimeSlot> timeslotList, List<Salle> SalleList, List<Seance> SeanceList) {
        this.timeslotList = timeslotList;
        this.salleList = SalleList;
        this.seanceList = SeanceList;
    }

    public List<TimeSlot> getTimeslotList() {
        return timeslotList;
    }

    public List<Salle> getRoomList() {
        return salleList;
    }

    public List<Seance> getLessonList() {
        return seanceList;
    }

    public HardSoftScore getScore() {
        return score;
    }

}