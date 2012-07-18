% Descendant Prolog Microlab
%
% Create A Descendant Procedure
%

% Family Rules:
%
parent(douglas,rosa).
parent(rita,rosa).
parent(douglas,gregory).
parent(rita,gregory).

parent(james,janet).
parent(rosa,janet).
parent(james,maria).
parent(rosa,maria).

parent(gregory,jason).
parent(erica,jason).

parent(george,bill).
parent(janet,bill).
parent(george,mary).
parent(janet,mary).
parent(george,jim).
parent(janet,jim).

parent(william,jose).
parent(maria,jose).
parent(william,juanita).
parent(maria,juanita).

parent(jason,cameron).
parent(brittany,cameron).
parent(jason,linda).
parent(brittany,linda).

%%<end!TopSection>
% Write a descendant procedure to use with a list
% of family rules:
% Rule: 'descendant(X,Y)'
%   - X is the descendant
%   - Y is the parent/ancestor
%   - Is true if Y is a parent or ancestor of X
%



%%<end!MidSection>
% Test function
%
%   - Calls the descendant rule, and prints out the boolean results
%
test(P1,P2) :- catch((descendant(P1,P2) -> write('Yes') ; write('No')), 
error(Err,_Context), (write('ERROR: '), write(Err))).

% getPeople function to get passed in people from command argument list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl Person1 Person2"
%    so we want the 9th and 10th arguments ('Person1' & 'Person2')
%
getPeople([_, _, _, _, _, _, _, _, Person1, Person2 |_], Person1, Person2).

% main function
%   - entry point for program
%   - will call test, which writes result to console
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl Person1 Person2
%
main :-
current_prolog_flag(argv, Args), getPeople(Args, Person1, Person2),
test(Person1, Person2), nl.

