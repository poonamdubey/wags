% IsMember Prolog Microlab
%
% Determines whether or not a number is a member of a list
%
%%<end!TopSection>
% Write a rule to determine whether or not a number is a
%  member of a list:
% Rule: 'mem(N, L)'
%   - N is the number to test
%   - L is the list
%



%%<end!MidSection>
% Test Function
% Since we need the results printed to the console,
%  we need to explicitly print 'true' and 'false'
%  - This simply calls the member rule, and explicitly
%    prints based on the boolean result
%
test(Atom, List) :- catch((mem(Atom, List) -> write('True') ; write('False')), 
error(Err,_Context), (write('ERROR: '), write(Err))).

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
%   - will call test, which will print result to console
%
%   Usage: /usr/local/bin/swipl -q -g main -t halt -f fileName.pl 2 1 2 3
%
main :-
current_prolog_flag(argv, Args), getAtom(Args, Atom), getList(Args, List),
test(Atom, List), nl.


