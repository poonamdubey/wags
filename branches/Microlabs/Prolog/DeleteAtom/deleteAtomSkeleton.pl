% Delete Atom Prolog Microlab
%
% Delete all occurrences of an atom
%
%%<end!TopSection>
% Write a rule to delete all occurrences of an atom
%  in a list:
% Rule: 'del(Atom, List, ResultList)'
%   - Atom is the atom to find and delete
%   - List is the original list
%   - ResultList has all occurrences of Atom removed
%



%%<end!MidSection>
%%<start!HiddenSection>
% Test function
%
%   - Calls the del rule, and if it does not fail, writes the resulting
%    list to the console, otherwise writes error message
%
test(Atom, List) :- catch((del(Atom, List, RL), write(RL)), error(Err,_Context),
 (write('ERROR: '), write(Err))).

% getList function to get last command arguments containing list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl NumArg ListArg"
%    so we want the tail ('ListArg') after the first 9 arguments
%
getList([_, _, _, _, _, _, _, _, _ |List], List).

% getAtom function to get passed in atom from command argument list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl AtomArg ListArg"
%    so we want the 9th argument ('AtomArg')
%
%   Note: This allows alpha-numeric input, all of which will be stored
%        characters
%
getAtom([_, _, _, _, _, _, _, _, Atom |_], Atom).

% main function
%   - entry point for program
%   - will call test, which writes result to console
%   - pass in atom to delete first, followed by list
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl 2 1 2 3
%
main :-
    current_prolog_flag(argv, Args), getAtom(Args, Atom), getList(Args, List),
     test(Atom, List), nl.

