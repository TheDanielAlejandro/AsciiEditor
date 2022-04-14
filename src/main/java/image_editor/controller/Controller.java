package image_editor.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import ascii_panel.AsciiRaster;
import image_editor.model.AsciiChar;
import image_editor.model.Model;
import image_editor.view.*;
import image_editor.interfaces.*;

/**
 * DESIGN PATTERN MVC/ Observer-observable.
 * Controller for the image editor application.
 * @author danie
 *
 */
public class Controller {
	
	/**
	 * SINGLETON DESING PATTERN
	 * Instance of the Controller.
	 */
	private static Controller instance;

	/**
	 * Instance of the Model
	 */
	private final Model M;
	
	/**
	 * Instance of the view ImageNew.
	 */
	private final ImageNew IMAGE_NEW_VIEW;
	
	/**
	 * Instance of the view ImageEditor.
	 */
	private final ImageEditor IMAGE_EDITOR_VIEW;
	
	/**
	 * Instance of the view CharacterSelector.
	 */
	private final CharacterSelector CHARACTER_SELECTOR_VIEW;
	
	/**
	 * Instance of the view ImageImporter.
	 */
	private final ImageImporter IMAGE_IMPORTER_VIEW;

	/**
	 * Instance of the view SelectColorChanger.
	 */
	private final SelectColorChanger SELECT_COLOR_CHANGER_VIEW;
	
	/**
	 * Instance of the view DrawSquare.
	 */
	private final DrawSquare DRAW_SQUARE_VIEW;
	
	/**
	 * Instance of the view DrawRectangle.
	 */
	private final DrawRectangle DRAW_RECTANGLE_VIEW;
	
	/**
	 * Instance of the view DrawTriangle.
	 */
	private final DrawTriangle DRAW_TRIANGLE_VIEW;
	
	/**
	 * STRATEGY DESIGN PATTERN - for the filters applied to the convert.
	 */
	private ImageConvertStrategy convert;
	
	/**
	 * Method that retrieves the instance of the controller, if absent it creates one.
	 * @return instance Controller.
	 */
	public static Controller getInstance() {
		if(instance==null)
			instance = new Controller();
		return instance;
	}
	
	/**
	 * Constructor for the controller class.
	 */
	private Controller() {
	
		M = Model.getInstance();
		IMAGE_EDITOR_VIEW = ImageEditor.getInstance();
		IMAGE_NEW_VIEW = ImageNew.getInstance();
		CHARACTER_SELECTOR_VIEW = CharacterSelector.getInstance();
		IMAGE_IMPORTER_VIEW = ImageImporter.getInstance();
		SELECT_COLOR_CHANGER_VIEW = SelectColorChanger.getInstance();
		DRAW_SQUARE_VIEW = DrawSquare.getInstance();
		DRAW_RECTANGLE_VIEW = DrawRectangle.getInstance();
		DRAW_TRIANGLE_VIEW = DrawTriangle.getInstance();
		M.addObserver(IMAGE_EDITOR_VIEW);  	//adding the image editor as a observer for the model
		M.writeAString(M.getStartingString());
		M.setHistory();
		setImageEditor();
		setImageImporter();
		convert = null;
	}
	
	/**
	 * Inner class that manages the mouse actions that are made in the main panel.
	 * @author danie
	 *
	 */
	private class ActionEditorMouseController implements EditorMouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch (M.getTool()) {
				
				case 0:
					if (e.getButton() == 1) {
						M.writeCharOnPointer();
					}else{
						M.removeCharOnPointer();
					}
					break;
					
				case 1:
					M.pick();	
					break;
					
				case 2:
					if (e.getButton() == 1) {
						M.fill();		
					}else{
						M.unfill();
					}
					break;
				case 3:
					
					if (e.getButton() == 1) {
						if(M.getSelectHolder() != null) {
							//M.deselect();
							M.selectOperation(1, null, null);
						}
						M.selectStartingPoint();
					}
					break;
					
				case 4: 
					M.clearMatrix();
					break;
					
				case 5:
					M.paste();
					break;
				
				case 6:
					M.cloneStampPoint();
					break;
						
				case 7:
					if (e.getButton() == 1) {
						M.cloneStampDraw();		
					}else{
						M.setTool(6);
						M.setPointerSetted(false);
						M.setPointerCharBack();
					}
					break;
				
				case 8 :
					M.setX1Figure(Integer.parseInt(DRAW_SQUARE_VIEW.getSideSizeTextField().getText()) + M.getCoordinateXPointer() -1);
					M.setY1Figure(Integer.parseInt(DRAW_SQUARE_VIEW.getSideSizeTextField().getText()) +M.getCoordinateYPointer() -1);
					M.drawFigure(0,
							Integer.parseInt(DRAW_SQUARE_VIEW.getSizeBorderTextField().getText()),
							DRAW_SQUARE_VIEW.getForegroundBorderPanel().getBackground(),
							DRAW_SQUARE_VIEW.getBackgroundBorderPanel().getBackground(),
							DRAW_SQUARE_VIEW.getForegroundInsidePanel().getBackground(),
							DRAW_SQUARE_VIEW.getBackgroundInsidePanel().getBackground()
							);
					break;
					
				case 9 :
					M.setX1Figure(Integer.parseInt(DRAW_RECTANGLE_VIEW.getWidthTextField().getText()) + M.getCoordinateXPointer() -1);
					M.setY1Figure(Integer.parseInt(DRAW_RECTANGLE_VIEW.getHeightTextField().getText()) +M.getCoordinateYPointer() -1);
					M.drawFigure(0,
							Integer.parseInt(DRAW_RECTANGLE_VIEW.getSizeBorderTextField().getText()),
							DRAW_RECTANGLE_VIEW.getForegroundBorderPanel().getBackground(),
							DRAW_RECTANGLE_VIEW.getBackgroundBorderPanel().getBackground(),
							DRAW_RECTANGLE_VIEW.getForegroundInsidePanel().getBackground(),
							DRAW_RECTANGLE_VIEW.getBackgroundInsidePanel().getBackground()
							);
					break;
				case 10 :
					M.setX1Figure(Integer.parseInt(DRAW_TRIANGLE_VIEW.getBaseSizeTextField().getText()) + M.getCoordinateXPointer() -1);
					M.drawFigure(1,
							Integer.parseInt(DRAW_TRIANGLE_VIEW.getSizeBorderTextField().getText()),
							DRAW_TRIANGLE_VIEW.getForegroundBorderPanel().getBackground(),
							DRAW_TRIANGLE_VIEW.getBackgroundBorderPanel().getBackground(),
							DRAW_TRIANGLE_VIEW.getForegroundInsidePanel().getBackground(),
							DRAW_TRIANGLE_VIEW.getBackgroundInsidePanel().getBackground()
							);
					break;
					
				default:
					break;
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch(M.getTool()) {
			case 3:
				if(e.getButton()==1) {
					M.selectFinishPoint();
					//M.select();
					M.selectOperation(0, null, null);
				}
			}
			
		}
		
	}
	
	
	/**
	 * Inner class that manages the movements of the mouse in the main panel of the image editor application.
	 * @author danie
	 *
	 */
	private class ActionEditorMouseMotionController implements MouseMotionListener {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			IMAGE_EDITOR_VIEW.onCursorMove(e.getButton(), e.getX(), e.getY());
			M.setCoordinatesPointer(e.getX()/16, e.getY()/16);
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		
			IMAGE_EDITOR_VIEW.onCursorMove(e.getButton(), e.getX(), e.getY());
			M.setCoordinatesPointer(e.getX()/16, e.getY()/16);
			
		}
		
	}
		
	/**
	 * Inner class that handles the logic behind the event "Create New" from the window ASCII ImageEditor-New.
	 * @author danie
	 *
	 */
	private class ActionNewController implements ActionListener	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				
				M.createNewMatrix(Integer.parseInt(IMAGE_NEW_VIEW.getWidthText().getText()), Integer.parseInt(IMAGE_NEW_VIEW.getHeightText().getText()));
				IMAGE_NEW_VIEW.setVisible(false);
				setMainPanel();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Inner class that handles the logic behind the event "Import" from the window ASCII ImageEditor - Import.
	 * @author danie
	 *
	 */
	private class ActionImportController implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("resources/");
			int returnVal = fileChooser.showOpenDialog(IMAGE_IMPORTER_VIEW);
			//System.out.println(returnVal);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					M.setImportedImage(ImageIO.read(new File(fileChooser.getSelectedFile().getAbsolutePath())));
					System.out.println("Resizing...");
					
					int tx = M.getMatrixColumns(); 
					int ty = M.getMatrixRows();  

					BufferedImage resized = new BufferedImage(tx, ty, M.getImportedImage().getType());
					Graphics2D g = resized.createGraphics();
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.drawImage(M.getImportedImage(), 0, 0, tx, ty, 0, 0,
							M.getImportedImage().getWidth(), M.getImportedImage().getHeight(),
							null);
					g.dispose();
					M.setImportedImage(resized);
					} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
			
				}
		}
		
	}
	
	/**
	 * Inner class that handles the logic behind the event "Convert" from the window ASCII ImageEditor - Import.
	 * @author danie
	 *
	 */
	private class ActionConvertController implements ActionListener {

		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(IMAGE_IMPORTER_VIEW.getNoneButton().isSelected()) {
				convert = new ThresholdConvertBehaviour();
			}else if(IMAGE_IMPORTER_VIEW.getBwButton().isSelected()) {
				convert = new BlackWhiteConvertBehaviour();
			}else if(IMAGE_IMPORTER_VIEW.getNegativeButton().isSelected()) {
				convert = new NegativeConvertBehaviour();
			}else if(IMAGE_IMPORTER_VIEW.getSmoothButton().isSelected()) {
				convert = new SmoothConverterBehaviour();
			}
			
			convert.convertImage(IMAGE_EDITOR_VIEW, IMAGE_IMPORTER_VIEW, M);
		}	
	
	}

	/**
	 * Inner class that handles the logic behind the event of the character selection from the window ASCII ImageEditor.	
	 * @author danie
	 *
	 */
	private class ActionCharacterSelectorController implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String s = e.getSource().toString().substring(21);
			String xS = s.substring(0, s.indexOf(','));
			String s2 = s.substring(s.indexOf(',')+1);
			String yS = s2.substring(0, s2.indexOf(','));
			int x = Integer.parseInt(xS);
			int y = Integer.parseInt(yS);
			int bottone = x/16+y;
			M.setCharInUse(new AsciiChar(bottone, Color.BLACK, Color.WHITE));
		}
		
	}
	
	/**
	 * Inner class that handles the logic behind the event of the "Save" action from the window ASCII ImageEditor.
	 * @author danie
	 *
	 */
	private class ActionSaveController implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("resources/");
			int returnVal = fileChooser.showSaveDialog(IMAGE_EDITOR_VIEW);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				IMAGE_EDITOR_VIEW.mainPanel.save(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	/**
	 * Inner class that handles the logic behind the event "Load" from the window ASCII ImageEditor.
	 * @author danie
	 *
	 */
	private class ActionLoadController implements ActionListener	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fileChooser = new JFileChooser("resources/");
			int returnVal = fileChooser.showOpenDialog(IMAGE_EDITOR_VIEW.mainPanel);	
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				//System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
				AsciiRaster rast = AsciiRaster.fromFile(fileChooser.getSelectedFile().getAbsolutePath());
				M.loadRaster(rast);
				setMainPanel();
		
			}
			
		}
		
	}


	/**
	 * Method that set's the action listeners to the main panel of the Image editor application.
	 */
	private void setMainPanel() {
		IMAGE_EDITOR_VIEW.mainPanel.addMouseListener(new ActionEditorMouseController());
		IMAGE_EDITOR_VIEW.mainPanel.addMouseMotionListener(new ActionEditorMouseMotionController());
	}
	
	/**
	 * Method that set's the functioning logic(listeners) and visibility of the imageEditor view.
	 */
	private void setImageEditor() {
		IMAGE_EDITOR_VIEW.setVisible(true);
		setMainPanel();
		
		//Controls panel
		
		//Characters
		IMAGE_EDITOR_VIEW.getPreviousCharButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setCharInUseToPrevious();}
		});
		
		IMAGE_EDITOR_VIEW.getCharSelectorButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setCharacterSelector();}
		});
		
		IMAGE_EDITOR_VIEW.getNextCharButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setCharInUseToNext();}
		});
		
		//Tools
		IMAGE_EDITOR_VIEW.getPaintButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(0);}
		});
		
		IMAGE_EDITOR_VIEW.getPickButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(1);}
			
		});
		
		IMAGE_EDITOR_VIEW.getFillButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(2);}
			
		});
		
		IMAGE_EDITOR_VIEW.getCloneStampButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(6);}
			
		});
		
		//Colors
		IMAGE_EDITOR_VIEW.getForegroundPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(IMAGE_EDITOR_VIEW.getForegroundPanel(), "Choose Foreground Color", IMAGE_EDITOR_VIEW.getForegroundPanel().getBackground());
				if (newColor != null) M.setCharForeColorInUse(newColor);	
			}
		});;
		
		IMAGE_EDITOR_VIEW.getBackgroundPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(IMAGE_EDITOR_VIEW.getBackgroundPanel(), "Choose Foreground Color", IMAGE_EDITOR_VIEW.getBackgroundPanel().getBackground());
				if (newColor != null) M.setCharBackColorInUse(newColor);	
			}
		});;
		
		
		//Undo/Redo
		IMAGE_EDITOR_VIEW.getUndoButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.undo();}
		});
		
		IMAGE_EDITOR_VIEW.getRedoButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.redo();}
		});
		
		// JMenu
		
		//File
		IMAGE_EDITOR_VIEW.getMbFileNew().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setImageNew();}
		});
		
		IMAGE_EDITOR_VIEW.getMbFileLoad().addActionListener(new ActionLoadController());
		
		IMAGE_EDITOR_VIEW.getMbFileSave().addActionListener(new ActionSaveController());
		
		IMAGE_EDITOR_VIEW.getMbFileImport().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {IMAGE_IMPORTER_VIEW.setVisible(true);}
		});
		
		//Select
		
		IMAGE_EDITOR_VIEW.getMbEditSelect().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(3);}
		});
		
		IMAGE_EDITOR_VIEW.getMbEditCopy().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.copy();}
		});
		
		IMAGE_EDITOR_VIEW.getMbEditPaste().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(5);}
		});
		
		IMAGE_EDITOR_VIEW.getMbEditCut().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {/*M.cut()*/ M.selectOperation(2, null, null);}
		});
		
		IMAGE_EDITOR_VIEW.getMbEditSmooth().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {/*M.smooth();*/ M.selectOperation(3, null, null);}
		});
		
		IMAGE_EDITOR_VIEW.getMbEditChangeColors().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setSelectColorChanger();}
		});
		
		//Draw
		
		IMAGE_EDITOR_VIEW.getMbDrawRectangle().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setDrawRectangle();}
		});
		
		IMAGE_EDITOR_VIEW.getMbDrawSquare().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setDrawSquare();}
		});
		
		IMAGE_EDITOR_VIEW.getMbDrawTriangle().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {setDrawTriangle();}
		});
	}

	/**
	 * Method that set's the functioning logic(listeners) and visibility of the CharacterSelector view.
	 */
	private void setCharacterSelector() {
		CHARACTER_SELECTOR_VIEW.setAsciiCharsPalette(IMAGE_EDITOR_VIEW.mainPanel.getGlyphs());
		CHARACTER_SELECTOR_VIEW.getAsciiCharsPalette().forEach(entry -> entry.addActionListener(new ActionCharacterSelectorController()));
		CHARACTER_SELECTOR_VIEW.setVisible(true);
	}
	
	/**
	 * Method that set's the functioning logic(listeners) of the ImageImporter view. 
	 */
	private void setImageImporter() {
			
		IMAGE_IMPORTER_VIEW.getImportButton().addActionListener(new ActionImportController());
		IMAGE_IMPORTER_VIEW.getConvertButton().addActionListener(new ActionConvertController());
		
	}
	
	/**
	 * Method that set's the functioning logic(listeners) and visibility of the imageNew view.
	 */
	private void setImageNew() {
		IMAGE_NEW_VIEW.setVisible(true);
		IMAGE_NEW_VIEW.getProceedButton().addActionListener(new ActionNewController());
	}
	
	
	private void setSelectColorChanger() {
		SELECT_COLOR_CHANGER_VIEW.setVisible(true);
		
		SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel(), "Choose background select color", SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel().getBackground());
				SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel().setBackground(newColor);
			}
		});
		
		
		SELECT_COLOR_CHANGER_VIEW.getForegroundPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(SELECT_COLOR_CHANGER_VIEW.getForegroundPanel(), "Choose background select color", SELECT_COLOR_CHANGER_VIEW.getForegroundPanel().getBackground());	
				SELECT_COLOR_CHANGER_VIEW.getForegroundPanel().setBackground(newColor);
			}
		});
		
		
		SELECT_COLOR_CHANGER_VIEW.getApplyButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*M.updateColors(SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel().getBackground(), SELECT_COLOR_CHANGER_VIEW.getForegroundPanel().getBackground());
				 */
				M.selectOperation(4, SELECT_COLOR_CHANGER_VIEW.getBackgroundPanel().getBackground(), SELECT_COLOR_CHANGER_VIEW.getForegroundPanel().getBackground());
			}
		});
		
	}
	
	private void setDrawSquare() {
		
		DRAW_SQUARE_VIEW.setVisible(true);
		
		DRAW_SQUARE_VIEW.getBackgroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override 
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_SQUARE_VIEW.getBackgroundBorderPanel(), "Choose border background", DRAW_SQUARE_VIEW.getBackgroundBorderPanel().getBackground());
				DRAW_SQUARE_VIEW.getBackgroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_SQUARE_VIEW.getForegroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_SQUARE_VIEW.getForegroundBorderPanel(), "Choose border foreground", DRAW_SQUARE_VIEW.getForegroundBorderPanel().getBackground());
				DRAW_SQUARE_VIEW.getForegroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_SQUARE_VIEW.getBackgroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_SQUARE_VIEW.getBackgroundInsidePanel(), "Choose inside background", DRAW_SQUARE_VIEW.getBackgroundInsidePanel().getBackground());
				DRAW_SQUARE_VIEW.getBackgroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_SQUARE_VIEW.getForegroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_SQUARE_VIEW.getForegroundInsidePanel(), "Choose inside background", DRAW_SQUARE_VIEW.getForegroundInsidePanel().getBackground());
				DRAW_SQUARE_VIEW.getForegroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_SQUARE_VIEW.getDrawButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(8);}
		});
	}
	
	private void setDrawRectangle() {
		
		DRAW_RECTANGLE_VIEW.setVisible(true);
		
		DRAW_RECTANGLE_VIEW.getBackgroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_RECTANGLE_VIEW.getBackgroundBorderPanel(), "Choose border background", DRAW_RECTANGLE_VIEW.getBackgroundBorderPanel().getBackground());
				DRAW_RECTANGLE_VIEW.getBackgroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_RECTANGLE_VIEW.getForegroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_RECTANGLE_VIEW.getForegroundBorderPanel(), "Choose border foreground", DRAW_RECTANGLE_VIEW.getForegroundBorderPanel().getBackground());
				DRAW_RECTANGLE_VIEW.getForegroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_RECTANGLE_VIEW.getBackgroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_RECTANGLE_VIEW.getBackgroundInsidePanel(), "Choose inside background", DRAW_RECTANGLE_VIEW.getBackgroundInsidePanel().getBackground());
				DRAW_RECTANGLE_VIEW.getBackgroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_RECTANGLE_VIEW.getForegroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_RECTANGLE_VIEW.getForegroundInsidePanel(), "Choose inside background", DRAW_RECTANGLE_VIEW.getForegroundInsidePanel().getBackground());
				DRAW_RECTANGLE_VIEW.getForegroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_RECTANGLE_VIEW.getDrawButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(9);}
		});
		
	}
	
	private void setDrawTriangle() {
		
		DRAW_TRIANGLE_VIEW.setVisible(true);
		
		DRAW_TRIANGLE_VIEW.getBackgroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override 
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_TRIANGLE_VIEW.getBackgroundBorderPanel(), "Choose border background", DRAW_TRIANGLE_VIEW.getBackgroundBorderPanel().getBackground());
				DRAW_TRIANGLE_VIEW.getBackgroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_TRIANGLE_VIEW.getForegroundBorderPanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_TRIANGLE_VIEW.getForegroundBorderPanel(), "Choose border foreground", DRAW_TRIANGLE_VIEW.getForegroundBorderPanel().getBackground());
				DRAW_TRIANGLE_VIEW.getForegroundBorderPanel().setBackground(newColor);
			}
		});
		
		DRAW_TRIANGLE_VIEW.getBackgroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_TRIANGLE_VIEW.getBackgroundInsidePanel(), "Choose inside background", DRAW_TRIANGLE_VIEW.getBackgroundInsidePanel().getBackground());
				DRAW_TRIANGLE_VIEW.getBackgroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_TRIANGLE_VIEW.getForegroundInsidePanel().addMouseListener(new ColorPanelsMouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(DRAW_TRIANGLE_VIEW.getForegroundInsidePanel(), "Choose inside background", DRAW_TRIANGLE_VIEW.getForegroundInsidePanel().getBackground());
				DRAW_TRIANGLE_VIEW.getForegroundInsidePanel().setBackground(newColor);
			}
		});
		
		DRAW_TRIANGLE_VIEW.getDrawButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {M.setTool(10);}
		});
		
	}
	
	
	public static void main(String[] args) {
		Controller.getInstance();
	}
	
}
