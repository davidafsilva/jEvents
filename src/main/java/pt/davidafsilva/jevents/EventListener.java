/*
 * Copyright (c) 2014, David Silva
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *         * Redistributions of source code must retain the above copyright
 *             notice, this list of conditions and the following disclaimer.
 *         * Redistributions in binary form must reproduce the above copyright
 *             notice, this list of conditions and the following disclaimer in the
 *             documentation and/or other materials provided with the distribution.
 *         * Neither the name of the <organization> nor the
 *             names of its contributors may be used to endorse or promote products
 *             derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pt.davidafsilva.jevents;

import java.lang.reflect.ParameterizedType;

/**
 * Defines the listener interface which should be implemented
 * by a specific {@link pt.davidafsilva.jevents.Event event} listener or
 * by an anonymous implementation.
 *
 * Listeners should be simple and handled quickly, in order to achieve a high throughput.
 * Heavy handling should be done in another thread.
 *
 * @param <T>
 * 		the type of the event which the listener is bound to
 * @author David Silva
 * @since 1.0
 */
public interface EventListener<T extends Event> {

	/**
	 * Returns the event class associated with the listener.
	 *
	 * By default with i'll extract the generic class denoted by <code>T</code>,
	 * associated with the EventListener interface.
	 *
	 * It does not support listeners inheritance.
	 *
	 * @return the event class
	 */
	@SuppressWarnings("unchecked")
	default Class<T> getEventClass() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
	}

	/**
	 * Handles a previously dispatched event
	 *
	 * @param event
	 * 		the event object
	 */
	void handle(T event);
}
