#language: fr

Fonctionnalité: : créer un usager

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | ROADWORK     |
      | LOCAL_POLICE |
      | SECRETARIAT  |
      | TECHNICAL    |
      | SCHOOL       |
      | PADDING_POOL |
      | MANAGEMENT   |

    Etant donné que des utilisateurs existent
      | prenom | nom       | role          | services     | avecOuSansEnfantMineur | email              |
      | Elise  | Moualala  | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com     |
      | Arthur | Rololo    | USER          | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE | sans                   | delapampa@test.com |

  Plan du scénario: en tant qu'administrateur, je peux créer de nouveaux usagers
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Alors l'usager "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>"  enfant mineur "<email>", existe dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | prenom    | nom         | services                          | avecOuSansEnfantMineur | email         |
      | Robert    | Dupont      | ROADWORK                          | sans                   | add1@test.com |
      | Sébastien | Vermelin    | LOCAL_POLICE                      | avec                   | add1@test.com |
      | Fathia    | Chaipa      | SECRETARIAT                       | sans                   | add1@test.com |
      | Max       | Lamenace    | TECHNICAL                         | avec                   | add1@test.com |
      | José      | De la pampa | TECHNICAL, ROADWORK               | avec                   | add1@test.com |
      | Louise    | Labrocante  | SCHOOL, PADDING_POOL, SECRETARIAT | sans                   | add1@test.com |

  Plan du scénario: en tant qu'administrateur, je ne peux pas créer plusieurs fois le meme usager
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Et je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Alors Une erreur signalant que l'usager existe déjà est produite
    Et l'usager "<nom>" "<prenom>" n'existe qu'une fois dans l'application
    Exemples:
      | prenom | nom    | services | avecOuSansEnfantMineur | email         |
      | Robert | Dupont | ROADWORK | sans                   | add1@test.com |

  Plan du scénario: en tant qu'administrateur, je peux recréer un usage qui a été effacé
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Et que je supprime un USAGER "<nom>" "<prenom>"
    Et que je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Alors l'usager "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>"  enfant mineur "<email>", existe dans l'application
    Et l'usager "<nom>" "<prenom>" n'existe qu'une fois dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | prenom | nom    | services | avecOuSansEnfantMineur | email         |
      | Robert | Dupont | ROADWORK | sans                   | add1@test.com |

  Plan du scénario: en tant qu'usager, je ne peux pas créer un usager
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom    | services | avecOuSansEnfantMineur | email         |
      | Robert | Dupont | ROADWORK | sans                   | add1@test.com |

  Plan du scénario: en tant qu'usager, je ne peux pas créer un usager
    Etant donné que je suis connecté en tant que MANAGER
    Quand je crée un USAGER "<nom>" "<prenom>" dans le service "<services>", "<avecOuSansEnfantMineur>" enfant mineur "<email>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | prenom | nom    | services | avecOuSansEnfantMineur | email         |
      | Robert | Dupont | ROADWORK | sans                   | add1@test.com |
