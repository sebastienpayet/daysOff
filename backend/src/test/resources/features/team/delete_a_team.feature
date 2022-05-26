#language: fr

Fonctionnalité: : effacer une équipe

  Contexte:
    Etant donné que des equipes existent
      | nom          |
      | MANAGEMENT   |
      | LOCAL_POLICE |
      | EMPTY_TEAM   |

    Etant donné que des utilisateurs existent
      | prenom | nom       | role          | services     | avecOuSansEnfantMineur | email              |
      | Elise  | Moualala  | ADMINISTRATOR | MANAGEMENT   | avec                   | elise@test.com     |
      | Arthur | Rololo    | USER          | LOCAL_POLICE | sans                   | arthur@test.com    |
      | Jose   | DeLaPampa | TEAM_MANAGER  | LOCAL_POLICE | sans                   | delapampa@test.com |

  Plan du scénario: en tant qu'administrateur, je peux supprimer une équipe
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je supprime l'équipe "<nom>"
    Alors l'équipe "<nom>" n'existe pas dans l'application
    Et aucune erreur n'a été produite
    Exemples:
      | nom        |
      | EMPTY_TEAM |

  Plan du scénario: en tant que manager, je ne peux pas supprimer une équipe
    Etant donné que je suis connecté en tant que MANAGER
    Quand je supprime l'équipe "<nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom        |
      | EMPTY_TEAM |

  Plan du scénario: en tant qu'utilisateur, je ne peux pas supprimer une équipe
    Etant donné que je suis connecté en tant qu'USAGER
    Quand je supprime l'équipe "<nom>"
    Alors Une erreur signalant que je n'ai pas les droits est produite
    Exemples:
      | nom          |
      | LOCAL_POLICE |

  Plan du scénario: en tant qu'administrateur, je ne peux pas supprimer une équipe qui comporte des membres
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je supprime l'équipe "<nom>"
    Alors Une erreur signalant que cette équipe est attachée a des membres est levée
    Exemples:
      | nom          |
      | LOCAL_POLICE |

  Plan du scénario: en tant qu'administrateur, je peux réutiliser le nom d'une équipe qui a été supprimée
    Etant donné que je suis connecté en tant qu'ADMINISTRATEUR
    Quand je crée une équipe "<nom>"
    Et que je supprime l'équipe "<nom>"
    Quand je crée une équipe "<nom>"
    Alors l'équipe "<nom>" existe dans l'application
    Exemples:
      | nom          |
      | LOCAL_POLICE |
