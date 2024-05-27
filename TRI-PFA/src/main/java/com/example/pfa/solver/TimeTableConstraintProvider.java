package com.example.pfa.solver;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import com.example.pfa.entities.Seance;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                roomConflict(constraintFactory),
                teacherConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                // Soft constraints are only implemented in the optaplanner-quickstarts code
        };
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory) {
        // A room can accommodate at most one lesson at the same time.

        // Select a lesson ...
        return constraintFactory
                .forEach(Seance.class)
                // ... and pair it with another lesson ...
                .join(Seance.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Seance::getTimeSlot),
                        // ... in the same room ...
                        Joiners.equal(Seance::getSalle),
                        // ... and the pair is unique (different id, no reverse pairs) ...
                        Joiners.lessThan(Seance::getId))
                // ... then penalize each pair with a hard weight.
                .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        // A teacher can teach at most one lesson at the same time.
        return constraintFactory.forEach(Seance.class)
                .join(Seance.class,
                        Joiners.equal(Seance::getTimeSlot),
                        // Get the teacher of the element
                        Joiners.equal(seance -> seance.getElement().getProfessor(), seance -> seance.getElement().getProfessor()),
                        Joiners.lessThan(Seance::getId))
                .penalize("Teacher conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        // A student can attend at most one Seance at the same time.
        return constraintFactory.forEach(Seance.class)
                .join(Seance.class,
                        Joiners.equal(Seance::getTimeSlot),
                        Joiners.equal(Seance::getGroupes),
                        Joiners.lessThan(Seance::getId))
                .penalize("Student group conflict", HardSoftScore.ONE_HARD);
    }
}
