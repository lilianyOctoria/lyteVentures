Feature: Source Lab Automation

  @SauceLab
  Scenario Outline: Complete Order
    Given User add to cart
    When User ProceedCart: <FIRST_NAME>, <LAST_NAME>, <ZIP>
    And User continue to submit
    Then User should see the cart complete

    Examples: 
      | FIRST_NAME | LAST_NAME | ZIP   |
      | LILIANY    | OCTORIA   | 17412 |

  @SauceLab
  Scenario Outline: Sorting Products
    Given User add to cart
    When User sort products based on: <DECISION>
    Then User should see the products sorted

    Examples: 
      | DECISION            |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |
