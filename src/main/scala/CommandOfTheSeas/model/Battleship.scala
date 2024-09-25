package CommandOfTheSeas.model

class Battleship(xPos: Int, yPos: Int, team: Int) extends GridNode(
  health = 200,
  range = 3,
  damage = 75,
  speed = 1,
  isPiece = true,
  xPos = xPos,
  yPos = yPos,
  team = team,
  imagePath = if (team == 1) "/CommandOfTheSeas/images/ShipBattleshipHull1.png" else "/CommandOfTheSeas/images/ShipBattleshipHull2.png",
  name = "Battleship"
)