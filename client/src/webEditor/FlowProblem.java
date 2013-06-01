package webEditor;

public class FlowProblem {
	public String[] variables, operators, conditions, answerChoices, solution;
	public String title, directions, dropPointPositions,arrowRelations;
	
	public FlowProblem(String title, String directions, String dropPointPositions, String arrowRelations, String[] variables,
			String[] operators, String[] conditions, String[] answerChoices, String[] solution){
		this.title = title;
		this.directions = directions;
		this.dropPointPositions = dropPointPositions;
		this.arrowRelations = arrowRelations;
		this.variables = variables;
		this.operators = operators;
		this.conditions = conditions;
		this.answerChoices = answerChoices;
		this.solution = solution;
	}
}
