% Define female relationship
female(jane).
female(anna).
female(mary).
female(catlyn).
female(sarah).

% Define male relationship
male(john).
male(jim).
male(peter).
male(alex).
male(tom).

% Define parent relationships
parent(john, jim).
parent(jane, jim).
parent(jim, anna).
parent(jim, tom).
parent(anna, peter).
parent(tom, mary).

% Define child relationships
child(X, Y) :- parent(Y, X).

% Define mother relationship
mother(X, Y) :- parent(X, Y), female(X).
mother(X) :- mother(X, _).

% Define father relationship
father(X, Y) :- parent(X, Y), male(X).
father(X) :- father(X, _).

% Define ancestor relationship
ancestor(X, Y) :- parent(X, Y).
ancestor(X, Y) :- parent(X, Y), ancestor(_, Y).

% Define descendant relationship
descendant(X, Y) :- ancestor(Y, X).

% Define blood relative relationship
blood_relative(X, Y) :- ancestor(Z, X), ancestor(Z, Y).

% Define sister relationship
sister(X, Y) :- parent(Z, X), parent(Z, Y), female(X), X \= Y.

% Define brother relationship
brother(X, Y) :- parent(Z, X), parent(Z, Y), male(X), X \= Y.

% Define aunt relationship
aunt(X, Y) :- parent(Z, Y), sister(X, Z).

% Define uncle relationship
uncle(X, Y) :- parent(Z, Y), brother(X, Z).

% Define nephew relationship
nephew(X, Y) :- parent(Y, Z), sister(Z, W), parent(W, X).

% Define niece relationship
niece(X, Y) :- parent(Y, Z), brother(Z, W), parent(W, X).

% Define person relationship
person(X) :- female(X).
person(X) :- male(X).

% Define woman relationsship
woman(X) :- person(X), not(male(X)).





