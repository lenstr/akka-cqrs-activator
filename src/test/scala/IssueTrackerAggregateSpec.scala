/*
 * Copyright 2017 Branislav Lazic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.time.LocalDate
import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.akkacqrs.IssueTrackerAggregate
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpec }

class IssueTrackerAggregateSpec extends WordSpec with Matchers with BeforeAndAfterAll {

  import org.akkacqrs.IssueTrackerAggregate._

  implicit val system = ActorSystem("issue-tracker-spec-system")

  "When issue is not created then IssueTrackerAggregate actor" should {
    val sender             = TestProbe()
    implicit val senderRef = sender.ref
    val uuid               = UUID.randomUUID()
    val description        = "Test description"
    val date               = LocalDate.now()

    val issueTrackerAggregate = system.actorOf(IssueTrackerAggregate.props(uuid))

    "correctly create a new issue" in {
      issueTrackerAggregate ! CreateIssue(uuid, description, date)
      sender.expectMsg(IssueCreated(uuid, description, date))
    }

    "not create an issue with same id again" in {
      issueTrackerAggregate ! CreateIssue(uuid, description, date)
      sender.expectMsg(IssueUnprocessed("Issue has been already created."))
    }

    "correctly update an issue" in {
      val updatedIssueDescription = "Updated issue description"
      val date                    = LocalDate.now()
      issueTrackerAggregate ! UpdateIssueDescription(uuid, updatedIssueDescription, date)
      sender.expectMsg(IssueDescriptionUpdated(uuid, updatedIssueDescription, date))
    }

    "correctly update an issue again" in {
      val updatedIssueDescription = "Updated issue description second time"
      val date                    = LocalDate.now()
      issueTrackerAggregate ! UpdateIssueDescription(uuid, updatedIssueDescription, date)
      sender.expectMsg(IssueDescriptionUpdated(uuid, updatedIssueDescription, date))
    }

    "correctly close an issue" in {
      issueTrackerAggregate ! CloseIssue(uuid, date)
      sender.expectMsg(IssueClosed(uuid, date))
    }

    "not close an issue again" in {
      issueTrackerAggregate ! CloseIssue(uuid, date)
      sender.expectMsg(IssueUnprocessed("Issue has been closed. Cannot update or close again."))
    }

    "correctly delete an issue" in {
      issueTrackerAggregate ! DeleteIssue(uuid, date)
      sender.expectMsg(IssueDeleted(uuid, date))
    }

    "not delete an issue again" in {
      issueTrackerAggregate ! DeleteIssue(uuid, date)
      sender.expectMsg(IssueUnprocessed("Issue has been deleted. Cannot update, close or delete again."))
    }
  }

  "When issue is not being created then IssueTrackerAggregate actor" should {
    val sender             = TestProbe()
    implicit val senderRef = sender.ref
    val uuid               = UUID.randomUUID()
    val date               = LocalDate.now()

    val issueTrackerAggregate = system.actorOf(IssueTrackerAggregate.props(uuid))

    "not update an issue" in {
      issueTrackerAggregate ! UpdateIssueDescription(uuid, "Updated description", date)
      sender.expectMsg(IssueUnprocessed("Create an issue first."))
    }

    "not close an issue" in {
      issueTrackerAggregate ! CloseIssue(uuid, date)
      sender.expectMsg(IssueUnprocessed("Create an issue first."))
    }

    "not delete an issue" in {
      issueTrackerAggregate ! DeleteIssue(uuid, date)
      sender.expectMsg(IssueUnprocessed("Create an issue first."))
    }
  }
}