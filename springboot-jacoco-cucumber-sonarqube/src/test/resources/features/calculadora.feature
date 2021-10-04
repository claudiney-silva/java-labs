Feature: Calculadora

  Scenario Outline: Soma
    Given eu quero somar dois números
    When eu informo <valor1> e <valor2>
    Then a soma é retornada
Examples:
  | valor1  | valor2 |
  | 100     | 5      |
  | 10      | 12     |
