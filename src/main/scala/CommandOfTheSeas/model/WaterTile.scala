package CommandOfTheSeas.model

class WaterTile(xPos: Int, yPos: Int) extends GridNode(
  health = 0,
  range = 0,
  damage = 0,
  speed = 0,
  isPiece = false,
  xPos = xPos,
  yPos = yPos,
  team = 0,
  imagePath = "/CommandOfTheSeas/images/water.png",
  name = "Empty tile"
)