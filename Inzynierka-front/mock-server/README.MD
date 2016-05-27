SERWER

/login POST
{
    email: String,
    password: String
}

w zwrotce:
{
    token: String,
    userId: ID USERA
}
lub
{
    status: error,
    msgs: String (lista)
}


/logout POST
{
    token: String
}

w zwrotce: nic


WSZYSTKIE POSTY POZA LOGIN mają mieć parametr token=STRING_TOKENU

/users/:id GET #INFORMACJE O UZYTKOWNIKU
w zwrotce:
{
    email: String,
    name: String,
    surname: String,
    id: String, #NUMER INDEKSU
    year: String
}

/users/:id/edit POST #ZMIANA INFORMACJI
{
    email: String,
    name: String,
    surname: String,
    id: String, #NUMER INDEKSU
    year: String
}

w zwrotce:
{
    status: OK
}

lub

{
    status: error,
    msgs: String (lista)
}

/users/new POST #REJESTRACJA
{
    email: String,
    name: String,
    surname: String,
    id: String, #NUMER INDEKSU
    year: String,
    password: String
}

w zwrotce:
{
    status: OK
}

lub
{
    status: error,
    title: String,
    detail:String
}

/offers/:id GET #OFERTA

w zwrotce:
{
    subject: ID,
    userId: ID usera,
    term: ID terminu aktualnego,
    preferredTerms: [ ID terminów ],
    preferredProfessor: [ ID prowadzacych ]
}

/offers GET #LISTA WSZYSTKICH OFERT

w zwrotce:
{
    offers: [{
                 subject: ID,
                 userId: ID usera,
                 term: ID terminu aktualnego,
                 preferredTerms: [ ID terminów ],
                 preferredProfessor: [ ID prowadzacych ]
             }],
    count: INTEGER #ILE JEST WSZYSTKICH
}

/offers?filter_date=01-01-2016 GET #LISTA OFERT OD ZALOGOWANIA
w zwrotce:
{
    offers: [{
                 subject: ID,
                 userId: ID usera,
                 term: ID terminu aktualnego,
                 preferredTerms: [ ID terminów ],
                 preferredProfessor: [ ID prowadzacych ]
             }],
    count: INTEGER #ILE JEST WSZYSTKICH
}

/users/:id/offers GET #LISTA TWOICH OFERT
w zwrotce:
{
    offers: [{
                 subject: ID,
                 userId: ID usera,
                 term: ID terminu aktualnego,
                 preferredTerms: [ ID terminów ],
                 preferredProfessor: [ ID prowadzacych ]
             }],
    count: INTEGER #ILE JEST WSZYSTKICH
}

/users/:id/offers/new POST #DODAJ OFERTE
{
     subject: ID,
     userId: ID usera,
     term: ID terminu aktualnego,
     preferredTerms: [ ID terminów ],
     preferredProfessor: [ ID prowadzacych ]
}

w zwrotce:
{
    status: OK
}

lub

{
    status: error,
    title: String,
    detail:String
}

/users/:id/offers/:offerId/edit POST #EDYCJA OFERTY
{
     subject: ID,
     userId: ID usera,
     term: ID terminu aktualnego,
     preferredTerms: [ ID terminów ],
     preferredProfessor: [ ID prowadzacych ]
}

w zwrotce:
{
    status: OK
}

lub

{
    status: error,
    title: String,
    detail:String
}

/users/:id/offers/:offerId DELETE #USUNIECIE OFERTY
w zwrotce:
{
    status: OK
}

lub

{
    status: error,
    title: String,
    detail:String
}


/subjects/:subject/list GET #LISTA przedmiotów wraz z terminami i prowadzacymi
w zwrotce:
{
     subject: ID,
     subjectName: String,
     terms: [{
        termId: ID terminu,
        professorId: ID profesora,
        termDate: data terminu,
        professorName: String imię i nazwisko profesora
     }]
}
