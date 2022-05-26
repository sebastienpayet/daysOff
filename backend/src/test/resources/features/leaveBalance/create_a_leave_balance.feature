#language: fr

Fonctionnalité: : créer un solde de congé pour un usager

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
      | prenom | nom         | role          | services   | avecOuSansEnfantMineur | email           |
      | Elise  | Malala      | ADMINISTRATOR | MANAGEMENT | avec                   | elise@test.com  |
      | José   | De la pampa | USER          | SCHOOL     | avec                   | jose@test.com   |
      | Louise | La Brocante | USER          | ROADWORK   | sans                   | louise@test.com |

  Plan du scénario: en tant qu'administrateur, je peux créer un solde de congé pour un usager
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Lorsque je crée un solde de congé de type '<type>' pour l'année '<annee>', d'un montant de '<montant1>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type '<type>' pour l'année '<annee>', d'un montant de '<montant2>' pour l'usager '<nom>' '<prenom>'
    Alors le solde de congé de type '<type>' pour l'année '<annee>', d'un montant de '<montant1>' pour l'usager '<nom>' '<prenom>' existe
    Et le solde de congé de type '<type>' pour l'année '<annee>', d'un montant de '<montant2>' pour l'usager '<nom>' '<prenom>' existe
    Et le solde total pour le type '<type>' pour l'année '<annee>' pour l'usager '<nom>' '<prenom>' est égal à '<montantTotal>'
    Et aucune erreur n'a été produite
    Exemples:
      | prenom | nom         | type                   | annee | montant1 | montant2 | montantTotal |
      | Louise | La Brocante | ANNUAL                 | 2021  | 12       | 10       | 22           |
      | Louise | La Brocante | SPECIAL                | 2021  | 6        | 10       | 16           |
      | Louise | La Brocante | SPECIAL                | 2020  | 4        | 10       | 14           |
      | Louise | La Brocante | SPECIAL                | 2020  | 8        | 10       | 18           |
      | Louise | La Brocante | WORKING_TIME_REDUCTION | 2021  | 16       | 10       | 26           |
      | Louise | La Brocante | RECOVERY               | 2019  | 3        | 10       | 13           |

  Plan du scénario: en tant qu'administrateur, je peux créer des soldes de congé pour un usager sur plusieurs années
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Lorsque je crée un solde de congé de type 'ANNUAL' pour l'année '2019', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'SPECIAL' pour l'année '2019', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'RECOVERY' pour l'année '2019', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'ANNUAL' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'SPECIAL' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'WORKING_TIME_REDUCTION' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'RECOVERY' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'RECOVERY' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'RECOVERY' pour l'année '2020', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'WORKING_TIME_REDUCTION' pour l'année '2021', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Et que je crée un solde de congé de type 'RECOVERY' pour l'année '2021', d'un montant de '<montant>' pour l'usager '<nom>' '<prenom>'
    Alors il existe '<expected2019>' types de soldes pour l'année '2019' pour l'usager '<nom>' '<prenom>'
    Et il existe '<expected2020>' types de soldes pour l'année '2020' pour l'usager '<nom>' '<prenom>'
    Et il existe '<expected2021>' types de soldes pour l'année '2021' pour l'usager '<nom>' '<prenom>'
    Et aucune erreur n'a été produite
    Exemples:
      | prenom | nom         | montant | expected2019 | expected2020 | expected2021 |
      | Louise | La Brocante | 12      | 3            | 4            | 2            |
