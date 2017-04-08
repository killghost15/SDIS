% 2.1

%estado inicial
estado_inicial(b(0,0)).

%estado final
estado_final(b(2,0)).

%transições entre estados
sucessor(b(X,Y), b(4,Y),C) :- X<4, C is 4-X.
sucessor(b(X,Y), b(X,3),C) :- Y<3, C is 3-Y.
sucessor(b(X,Y), b(0,Y),C) :- X>0, C is X.
sucessor(b(X,Y), b(X,0),C) :- Y>0, C is Y.
sucessor(b(X,Y), b(4,Y1),C) :-
			X+Y>=4,
			X<4,
			Y1 is Y-(4-X), C is 4-X.
sucessor(b(X,Y), b(X1,3),C) :-
			X+Y>=3,
			Y<3,
			X1 is X-(3-Y), C is 3-Y.
sucessor(b(X,Y), b(X1,0),C) :-
			X+Y<4,
			Y>0,
			X1 is X+Y,C is Y.
sucessor(b(X,Y), b(0,Y1),C) :-
			X+Y<3,
			X>0,
			Y1 is X+Y,C is X.
  

% a) Pesquisa em profundidade
% nao garante a solução otima, 
% nem garante encontrar solução, nao encontra se tiver um ciclo infinito ou se o espaço de dados for infinito
dfs(E, [E]) :- estado_final(E).
dfs(E, [E|Es]) :- 
  sucessor(E, E2),
  dfs(E2, Es).
  
  
solve_dfs(Sol) :-
  estado_final(Ei),
  dfs(Ei, Sol).

  
h(b(X,Y),H):-estado_final(b(Xf,Yf)), H is max(abs(X-Xf),abs(Y-Yf)).

% para garantir que nao fica preso num ciclo infinito
% guardar os nos visitados numa variavel Visit

dfs(E, -, [E]) :- estado_final(E).
dfs(E, Visit, [E|Es]) :- 
  sucessor(E, E2),  \+ member(E2, Visit),
  dfs(E2, [E2|Visit], Es).
  
  
solve_dfs(Sol) :-
  estado_final(Ei),
  dfs(Ei, [Ei], Sol).

% b) Pesquisa em largura
% encontra a solução ótima, a mais curta
bfs([[E|Cam]|_], [E|Cam]) :-
  estado_final(E).
bfs([[E|Cam]|R], Sol) :-
  findall([E2|[E|Cam]], sucessor(E, E2),  Ls), % colocar todos os sucessores de E2 em Ls
  append(R, Ls, L2), % L2 tem agora a lista de nos em aberto
  bfs(L2, Sol).
  
solve_bfs(Sol) :-
  estado_inicial(Ei),
  bfs([[Ei|[]]], Sol).
  
% Pesquisa em largura mas que evita ciclos, melhoria espacial
% encontra a solução ótima, a mais curta, tal como o outro
bfs([[E|Cam]|_], [E|Cam]) :-
  estado_final(E).
bfs([[E|Cam]|R], Sol) :-
  findall([E2|[E|Cam]], (sucessor(E, E2), \+ member(E2, [E|Cam])), Ls), % colocar todos os sucessores de E2 em Ls
  append(R, Ls, L2), % L2 tem agora a lista de nos em aberto
  bfs(L2, Sol).
  
solve_bfs(Sol) :-
  estado_inicial(Ei),
  bfs([[Ei|[]]], Sol).



solve_astar(Sol):-
estado_inicial(Ei),h(Ei,Hi),astar([(Hi,0,[Ei])],Sol).

astar([(F,G,[E|Cam])|_], (F,G,[E|Cam])):-
estado_final(E).

  astar([(_,G, [E|Cam])|R],Sol):- write(E:F:G),nl,
   findall((F2,G2,[E2|[E|Cam]]),  (sucessor(E, E2,C), G2 is G + C,h(E2,H2),F2 is G2 +H2), Ls),  % colocar todos os sucessores de E2 em Ls
  append(R, Ls, L2), sort(L2,L2ord), % L2 tem agora a lista de nos em aberto
  astar(L2ord, Sol).
