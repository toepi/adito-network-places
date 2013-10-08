
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.adito.networkplaces.store.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.vfs2.FileObject;

import com.adito.boot.SystemProperties;
import com.adito.networkplaces.AbstractNetworkPlaceMount;
import com.adito.policyframework.LaunchSession;
import com.adito.security.PasswordCredentials;
import com.adito.vfs.VFSStore;
import com.adito.vfs.utils.URI;
import com.adito.vfs.webdav.DAVUtilities;

/**
 * Implementation of a {@link AbstractNetworkPlaceMount} that provides mounts
 * to the local file system.
 */
public class FileMount extends AbstractNetworkPlaceMount {

    /**
     * Constructor.
     *
     * @param launchSession launch session
     * @param store store
     */
    public FileMount(LaunchSession launchSession, VFSStore store) {
        super(launchSession, store);
    }

    /* (non-Javadoc)
     * @see com.adito.networkplaces.AbstractNetworkPlaceMount#createVFSFileObject(java.lang.String, com.adito.security.PasswordCredentials)
     */
    public FileObject createVFSFileObject(String path, PasswordCredentials credentials) throws IOException {
        URI uri = getRootVFSURI();
        uri.setPath(DAVUtilities.concatenatePaths(uri.getPath(), path));
        if(uri.getPath().startsWith("./")) {
	        FileObject root = getStore().getRepository().getFileSystemManager().resolveFile(new File(SystemProperties.get("user.dir")), uri.getPath().substring(2));
	        return root;
        }
        else {
	        FileObject root = getStore().getRepository().getFileSystemManager().resolveFile(uri.toString());
	        return root;
        }
    }
}