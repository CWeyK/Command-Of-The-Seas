package CommandOfTheSeas.model

class Carrier(xPos: Int, yPos: Int, team: Int) extends GridNode(
  health = 125,
  range = 4,
  damage = 75,
  speed = 1,
  isPiece = true,
  xPos = xPos,
  yPos = yPos,
  team = team,
  imagePath = if (team == 1) "/CommandOfTheSeas/images/ShipCarrier1.png" else "/CommandOfTheSeas/images/ShipCarrier2.png",
  name = "Carrier"
)