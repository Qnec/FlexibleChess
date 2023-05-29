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
        for(MoveReference move : this.getPossibleMoveReferences(game, piece, index)) {
            //System.out.println(game);
            //System.out.println(piece);
            //System.out.println(this);
            //System.out.println(move);
            //System.out.println(this.isMoveValid(game, piece, this, move));
            //System.out.println(this.isMoveValid(game, piece, this, move));
            //move.translateSelf(game, piece);
            if(this.isMoveValid(game, piece, this, move)) {
                output.add(move.getTranslatedSelf(game, piece));
            }
        }
        return output;
    }

    public boolean isMoveValid(Game game, Piece piece, Move move, MoveReference moveReference) {
        //System.out.println("thing2");
        //System.out.println(moveReference);
        MoveReference translated = moveReference.getTranslatedSelf(game, piece);
        //System.out.println(moveReference);

        for(Requirement requirement : this.requirements) {
            //System.out.println(requirement);
            if(!requirement.isMet(game, piece, move, moveReference)) {
                //System.out.println("failed");
                return false;
            }
        }
        Area gameArea = new Area(new Position(0,0), game.gt.dimensions);
        if(!gameArea.contains(translated.finish)) {
            //System.out.println(thing);
            //System.out.println(thing.contains(moveReference.finish));
            //System.out.println(moveReference.finish);
            return false;
        }
        return true;
    }

    public boolean executeMove(Game game, Piece piece, Move move, MoveReference moveReference) {
        //System.out.println("thing7");
        //System.out.println(this.actions.length);
        for(Action action : this.actions) {
            //System.out.println(action);
            action.executeAction(game, piece, moveReference);
        }
        //System.out.println(game);
        game.movePiece(piece, moveReference.getTranslatedSelf(game, piece).finish);
        //System.out.println(game);
        return true;
    }
}
