% ListLength Prolog Microlab
%
% Find length of a list (non-nested)
%
% Write a rule to find the number of atoms
%  in a list (non-nested):
% Rule: 'listLength(List, Length)'
%   - List is the original list
%   - Length is the number of atoms in the list
%
listLength([],0).
listLength([_|Y], Length):- listLength(Y,LengthMinus1), 
    Length is LengthMinus1 + 1.


% Test function for list
%
%   - Calls the listLength rule, and if it does not fail, writes the length
%    of the list to the console, otherwise writes error message
%
test(L) :- catch((listLength(L, Length), write(Length)), error(Err,_Context),
 (write('ERROR: '), write(Err))).

% getList function to get last command arguments containing list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl ListArg"
%    so we want the tail ('ListArg') after the first 8 arguments
%
getList([_, _, _, _, _, _, _, _ |List], List). 

% main function
%   - entry point for program
%   - will call test, passing in given list (non-nested)
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl 1 2 3
%
main :-
    current_prolog_flag(argv, Args), getList(Args, List),
    test(List), nl.

