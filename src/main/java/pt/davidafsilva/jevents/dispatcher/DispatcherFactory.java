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

package pt.davidafsilva.jevents.dispatcher;

import pt.davidafsilva.jevents.Event;

/**
 * The dispatcher factory.
 *
 * @author David Silva
 * @since 1.0
 */
enum DispatcherFactory {
	INSTANCE;

	/**
	 * Creates a new instance of the appropriate dispatcher, either
	 * a synchronous ou asynchronous.
	 *
	 * @param async
	 * 		the async flag
	 * @param <E>
	 * 		the event type associated with the dispatcher
	 * @return the dispatcher
	 */
	<E extends Event> Dispatcher<E> create(boolean async) {
		final Dispatcher<E> dispatcher;
		if (async) {
			dispatcher = new AsyncDispatcher<>();
		} else {
			dispatcher = new SyncDispatcher<>();
		}

		return dispatcher;
	}
}
