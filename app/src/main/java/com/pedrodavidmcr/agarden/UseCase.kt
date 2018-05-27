package com.pedrodavidmcr.agarden

import com.pedrodavidmcr.agarden.executor.Result


interface UseCase {
  fun execute(): Result
}