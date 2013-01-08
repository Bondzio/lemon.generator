/**********************************************************************************
 * Copyright (c) 2011, Monnet Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Monnet Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE MONNET PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *********************************************************************************/
package eu.monnetproject.lemon.stl.test;

import eu.monnetproject.label.LabelExtractorFactory;
import eu.monnetproject.lemon.generator.GeneratorActor;
import eu.monnetproject.ontology.OntologySerializer;
import java.util.LinkedList;
import java.util.List;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author John McCrae
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        final ServiceReference osRef = context.getServiceReference(OntologySerializer.class.getName());
        OntologySerializer serializer = (OntologySerializer)context.getService(osRef);
        final ServiceReference[] actorRefs = context.getAllServiceReferences(GeneratorActor.class.getName(), null);
        final List<GeneratorActor> actors = new LinkedList<GeneratorActor>();
        for(ServiceReference ref : actorRefs) {
            final Object service = context.getService(ref);
            if(service != null) {
                actors.add((GeneratorActor)service);
            }
        }
        final ServiceReference lefRef = context.getServiceReference(LabelExtractorFactory.class.getName());
        LabelExtractorFactory lef = (LabelExtractorFactory)context.getService(lefRef);
        context.registerService(Object.class.getName(), new GeneratorAndPhraseRootTest(actors, serializer, lef), null);
        context.registerService(Object.class.getName(), new LelaGeneratorTest(actors, serializer, lef), null);
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
    }

}
