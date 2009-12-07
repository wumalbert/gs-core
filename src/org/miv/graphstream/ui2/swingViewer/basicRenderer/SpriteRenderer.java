/*
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 * 
 * Copyright 2006 - 2009
 * 	Julien Baudry
 * 	Antoine Dutot
 * 	Yoann Pigné
 * 	Guilhelm Savin
 */

package org.miv.graphstream.ui2.swingViewer.basicRenderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.miv.graphstream.ui2.graphicGraph.GraphicElement;
import org.miv.graphstream.ui2.graphicGraph.GraphicSprite;
import org.miv.graphstream.ui2.graphicGraph.StyleGroup;
import org.miv.graphstream.ui2.graphicGraph.stylesheet.StyleConstants;
import org.miv.graphstream.ui2.graphicGraph.stylesheet.Values;
import org.miv.graphstream.ui2.graphicGraph.stylesheet.StyleConstants.FillMode;
import org.miv.graphstream.ui2.swingViewer.util.Camera;
import org.miv.graphstream.ui2.swingViewer.util.GraphMetrics;

public class SpriteRenderer extends ElementRenderer
{
	protected GraphMetrics metrics;
	
	protected Values size;
	
	protected Ellipse2D shape;
	
	protected float width, height, w2, h2;
	
	@Override
    protected void setupRenderingPass( StyleGroup group, Graphics2D g, Camera camera )
    {
		metrics = camera.getMetrics();
    }

	@Override
    protected void pushDynStyle( StyleGroup group, Graphics2D g, Camera camera,
            GraphicElement element )
    {
		Color color = group.getFillColor( 0 );
		
		if( element != null && group.getFillMode() == FillMode.DYN_PLAIN )
			color = interpolateColor( group, element );
		
		g.setColor( color );
    }

	@Override
    protected void pushStyle( StyleGroup group, Graphics2D g, Camera camera )
    {
		size    = group.getSize();
		shape   = new Ellipse2D.Float();
		width   = metrics.lengthToGu( size, 0 );
		height  = size.size() > 1 ? metrics.lengthToGu( size, 1 ) : width;
		w2      = width  / 2;
		h2      = height / 2;
		
		Color color = group.getFillColor( 0 );
		
		g.setColor( color );
    }

	@Override
    protected void elementInvisible( StyleGroup group, Graphics2D g, Camera camera,
            GraphicElement element )
    {
    }

	@Override
    protected void renderElement( StyleGroup group, Graphics2D g, Camera camera,
            GraphicElement element )
    {
		GraphicSprite sprite = (GraphicSprite) element;
		Point2D.Float pos    = camera.getSpritePosition( sprite, new Point2D.Float(), StyleConstants.Units.GU );
		
		shape.setFrame( pos.x-w2, pos.y-h2, width, height );
		g.fill( shape );
    }
}