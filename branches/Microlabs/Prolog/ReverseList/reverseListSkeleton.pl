% ReverseList Prolog Microlab
%
% Reverses a list
%
%%<end!TopSection>
% Write a rule to reverse a list:
% Rule: 'rev(List, ReversedList)'
%   - List is the original list
%   - ReversedList has the same elements as List in reverse order
%



%%<end!MidSection>
%%<start!HiddenSection>
% Test function for list
%
%   - Calls the rev rule, and if it does not fail, writes the reversed
%    list to the console, otherwise writes error message
%
test(L) :- catch((rev(L, RL), write(RL)), error(Err,_Context),
 (write('ERROR: '), write(Err))).

% getList function to get last command arguments containing list
%   - the command line arguments when program is run are: 
%    "/usr/local/bin/swipl -q -g main -t halt -f fileName.pl ListArg"
%    so we want the tail ('ListArg') after the first 8 arguments
%
getList([_, _, _, _, _, _, _, _ |List], List). 

% main function
%   - entry point for program
%   - will call test, which writes result to console
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl 1 2 3
%
main :-
    current_prolog_flag(argv, Args), getList(Args, List),
    test(List), nl.

