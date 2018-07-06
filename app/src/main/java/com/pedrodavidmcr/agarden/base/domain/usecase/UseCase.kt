package com.pedrodavidmcr.agarden.base.domain.usecase

import com.pedrodavidmcr.agarden.base.executor.Result


interface UseCase {
  fun execute(): Result
}