package main.RequirementTypes;

import main.Area;

public class AreaContainsPiece extends Requirement {
    enum ContainsType {
        CONT,
        CONTONLY,
        NOTCONT,
        NOTCONTONLY
    }
    private Area area;
    private ContainsType type;
    private String pieceId;
    public AreaContainsPiece(RequirementType type, String[] parameters) {
        super(type);
        this.area = new Area(parameters[0]);
        this.type = ContainsType.valueOf(parameters[1]);
        this.pieceId = parameters[2];
    }
}
