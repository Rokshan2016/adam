/**
 * Copyright 2014 Genome Bridge LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bdgenomics.adam.rich

import org.bdgenomics.adam.models.{ ReferenceRegion, ReferenceMapping }
import org.bdgenomics.adam.avro.{ ADAMFlatGenotype, ADAMRecord }

/**
 * A common location in which to drop some ReferenceMapping implementations.
 */
object ReferenceMappingContext {

  implicit object ADAMFlatGenotypeReferenceMapping extends ReferenceMapping[ADAMFlatGenotype] with Serializable {
    override def getReferenceName(value: ADAMFlatGenotype): String = value.getReferenceName.toString
    override def getReferenceRegion(value: ADAMFlatGenotype): ReferenceRegion =
      ReferenceRegion(value.getReferenceName.toString, value.getPosition, value.getPosition)
  }

  implicit object ADAMRecordReferenceMapping extends ReferenceMapping[ADAMRecord] with Serializable {
    def getReferenceName(value: ADAMRecord): String =
      value.getContig.getContigName.toString

    def getReferenceRegion(value: ADAMRecord): ReferenceRegion =
      ReferenceRegion(value).getOrElse(null)
  }

  implicit object ReferenceRegionReferenceMapping extends ReferenceMapping[ReferenceRegion] with Serializable {
    def getReferenceName(value: ReferenceRegion): String =
      value.referenceName.toString

    def getReferenceRegion(value: ReferenceRegion): ReferenceRegion = value
  }

  implicit def adamRecordToReferenceMapped(rec: ADAMRecord): ReferenceMapping[ADAMRecord] =
    ADAMRecordReferenceMapping

  implicit def referenceRegionToReferenceMapped(reg: ReferenceRegion): ReferenceMapping[ReferenceRegion] =
    ReferenceRegionReferenceMapping
}