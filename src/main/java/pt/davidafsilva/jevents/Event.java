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

import pt.davidafsilva.jevents.dispatcher.EventDispatcher;

/**
 * Defines the base contract for the event object.
 *
 * All event objects must implement this interface, in order to be
 * fully compliant with the framework.
 *
 * @author David Silva
 * @since 1.0
 */
public interface Event {

	/**
	 * Dispatches this event synchronously
	 *
	 * @see pt.davidafsilva.jevents.dispatcher.EventDispatcher#dispatch(Event)
	 */
	default void dispatch() {
		if (isAsync()) {
			EventDispatcher.INSTANCE.dispatchAsync(this);
		} else {
			EventDispatcher.INSTANCE.dispatch(this);
		}
	}

	/**
	 * Returns whether or not this event is, by creation, asynchronous.
	 *
	 * By default, all events are synchronous.
	 *
	 * @return <code>true</code> if the event is asynchronous, <code>false</code> if it's synchronous.
	 */
	default boolean isAsync() {
		return false;
	}

}
