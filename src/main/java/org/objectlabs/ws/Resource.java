package org.objectlabs.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.objectlabs.ns.Namespace;
import org.objectlabs.ns.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/******************************************************************************
 * Resource
 *
 * @author William Shulman
 *
 * 05.10.2010
 */
public class Resource implements WebService {

    /*************************************************************************
     * logger
     */
    private static Logger logger =
            LoggerFactory.getLogger(Resource.class);

    private static Logger getLogger() {
        return(logger);
    }

    /*************************************************************************
     * name
     */
    private String name;

    public String getName() {
        if (name == null) {
            name = "";
        }
        return(name);
    }

    public void setName(String value) {
        name = value;
    }

    /*************************************************************************
     * absoluteName
     */
    public String getAbsoluteName() {
        String result = "";

        Namespace parent = getParent();
        if (parent == null) {
            result = "/" + getName();
        } else {
            result = parent.getAbsoluteName() + "/" + getName();
        }

        return(result);
    }

    /*************************************************************************
     * parent
     */
    private Namespace parent;

    public Namespace getParent() {
        return(parent);
    }

    public void setParent(Namespace value) {
        parent = value;
    }

    public Namespace getParent(Class c) {
        Namespace parent = getParent();
        if (parent == null) {
            return(null);
        }

        if (c.isAssignableFrom(parent.getClass())) {
            return(parent);
        }

        return(parent.getParent(c));
    }

    /*************************************************************************
     * root
     */
    public Namespace getRoot() {
        Namespace parent = getParent();
        if (parent == null) {
            return(this);
        }

        return(parent.getRoot());
    }

    /*************************************************************************
     * resolve
     */
    public Resource resolve(String name) {
        return(resolve(name == null ? null : new Uri(null, name)));
    }

    /*************************************************************************
     * resolve
     */
    public Resource resolve(Uri uri) {
        if (uri == null || uri.hasEmptyPath()) {
            return(this);
        }

        if (uri.isAbsolute()) {
            Namespace parent = getParent();
            if (parent != null) {
                return (Resource) parent.resolve(uri);
            }
        }

        return resolveRelative(uri);
    }

    /*************************************************************************
     * resolveRelative
     */
    public Resource resolveRelative(Uri uri) {
        return(null);
    }

    /**************************************************************************
     * service 
     */
    public void service(HttpServletRequest request,
                        HttpServletResponse response)
    {
        getLogger().info("service(): " + getName());
    }

}
