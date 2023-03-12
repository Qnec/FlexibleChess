package main;

import java.util.ArrayList;

import main.ActionTypes.Action;
import main.MovementTypes.Movement;
import main.Position.RelativeTo;
import main.RequirementTypes.Requirement;

public class Move {
    private enum Types {
        MOVEMENT,
        REQUIREMENT,
        ACTION,
        UNASSIGNED
    }
    
    private Movement[] movements;
    private Requirement[] requirements;
    private Action[] actions;
    public Move(Movement[] movements, Requirement[] requirements, Action[] actions) {
        this.movements = movements;
        this.requirements = requirements;
        this.actions = actions;
    }

    public static Move parseMove(String arguments) {
        String[] parameters = arguments.split(" ");
        ArrayList<Movement> movementsList = new ArrayList<Movement>();
        ArrayList<Requirement> requirementsList = new ArrayList<Requirement>();
        ArrayList<Action> actionsList = new ArrayList<Action>();
        ArrayList<String> section = new ArrayList<String>();
        Types currentSectionType = Types.UNASSIGNED;
        for(int i = 0; i < parameters.length; i++) {
            
            String parameter = parameters[i];
            //System.out.println(String.join(" ", Arrays.copyOfRange(parameters, from, to)));
            //System.out.println(parameter);
            boolean isMovement = Movement.isMovement(parameter);
            boolean isRequirement = Requirement.isReqirement(parameter);
            boolean isAction = Action.isAction(parameter);
            if(isMovement || isRequirement || isAction) {
                //System.out.println("thing");
                switch(currentSectionType) {
                    case MOVEMENT:
                    movementsList.add(Movement.parseMovement(section.toArray(new String[section.size()])));
                    break;
                    case REQUIREMENT:
                    requirementsList.add(Requirement.parseRequirement(section.toArray(new String[section.size()])));
                    break;
                    case ACTION:
                    actionsList.add(Action.parseAction(section.toArray(new String[section.size()])));
                    break;
                    case UNASSIGNED:
                    break;
                    default:
                    break;
                }
                section = new ArrayList<String>();
                if(isMovement) {
                    currentSectionType = Types.MOVEMENT;
                } else if(isRequirement) {
                    currentSectionType = Types.REQUIREMENT;
                } else if(isAction) {
                    currentSectionType = Types.ACTION;
                }
            }/* else {
                break;
            }*/
            section.add(parameter);
        }
        //System.out.println(movementsList.size());
        //System.out.println(requirementsList.size());
        //System.out.println(actionsList.size());
        return new Move(
            movementsList.toArray(new Movement[movementsList.size()]),
            requirementsList.toArray(new Requirement[requirementsList.size()]),
            actionsList.toArray(new Action[actionsList.size()]));
    }

    @Override
    public String toString() {
        return this.movements.length + " " + this.requirements.length + " " + this.actions.length;
    }

    public ArrayList<MoveReference> getPossibleMoveReferences(Game game, Piece piece, int index) {
        ArrayList<MoveReference> output = new ArrayList<MoveReference>();
        for(Movement movement : this.movements) {
            for(Position p : movement.getPossibleMovementPositions()) {
                output.add(new MoveReference(new Position(0,0, RelativeTo.START), p, piece.uuid, index));
            }
        }
        return output;
    }

    public ArrayList<MoveReference> getValidMoveReferences(Game game, Piece piece, int index) {
        ArrayList<MoveReference> output = new ArrayList<MoveReference>();
        return output;
    }
}
