package com.dagger.navermapclustering.clustering.geometry

class Bounds(val minX: Double, val maxX: Double, val minY: Double, val maxY: Double) {
	val midX: Double = (minX + maxX) / 2
	val midY: Double = (minY + maxY) / 2

	fun contains(x: Double, y: Double): Boolean {
		return x in minX..maxX && y in minY..maxY
	}

	operator fun contains(point: Point): Boolean {
		return contains(point.x, point.y)
	}

	fun intersects(minX: Double, maxX: Double, minY: Double, maxY: Double): Boolean {
		return minX < this.maxX && this.minX < maxX && minY < this.maxY && this.minY < maxY
	}

	fun intersects(bounds: Bounds): Boolean {
		return intersects(bounds.minX, bounds.maxX, bounds.minY, bounds.maxY)
	}

	operator fun contains(bounds: Bounds): Boolean {
		return bounds.minX >= minX && bounds.maxX <= maxX && bounds.minY >= minY && bounds.maxY <= maxY
	}
}

