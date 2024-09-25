package CommandOfTheSeas.model

class Cruiser(xPos: Int, yPos: Int, team: Int) extends GridNode(
  health = 150,
  range = 3,
  damage = 40,
  speed = 2,
  isPiece = true,
  xPos = xPos,
  yPos = yPos,
  team = team,
  imagePath = if (team == 1) "/CommandOfTheSeas/images/ShipCruiser1.png" else "/CommandOfTheSeas/images/ShipCruiser2.png",
  name = "Cruiser"
)