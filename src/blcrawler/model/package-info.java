/**
 * Package for all model-type classes. A model is first and foremost a representation of stored
 * data, and most classically fills the object oriented principle of objects representing "things."
 * CatalogPart, storing all information about a type of part, is an example of a model. 
 * <p>Content Guidelines:
 * 
 * <p><ul>
 * <li>A model should store all fields used to describe an object, except for those relevant to
 * displaying this information to the user. No Swing or JavaFX should be used in a model class, 
 * this belongs in View
 * 
 * <li>Data manipulation methods can be written within a model, but if they are not called from
 * other methods within the model itself, they should almost always be called by a corresponding
 * controller
 * 
 * <li>Models can have a wide range of sophistication. Single instance or complex classes should
 * always have a controller
 * 
 * <li>Models should interact with other models, in particular models which they are not the
 * parent of, through their controller rather than directly
 * 
 * <li>Extensive read/write operations, such as XML parsing, should be handled by controllers
 * 
 * <li>If a controller exists, it should always be the parent class
 */
/**
 * @author Joe Gallagher
 *
 */
package blcrawler.model;