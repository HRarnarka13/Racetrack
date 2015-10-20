//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by arnarkari on 18/10/15.
// *
// * @author arnarkari
// */
//public class Racetrack {
//
//    public static void main(String[] args) {
//
//        final int MAX_ITERATIONS = 30;
//        final int NUMBER_OF_EPISODES = 1000000;
//        final int OFF_REWARD = -5;
//        final int ON_REWARD = -1;
//
//        Actions actions = new Actions();
//        Track track;
//        try {
//            track = new Track(TrackReader.TrackReader("track1"));
//            System.out.println(track);
//
//            HistoryHash history = new HistoryHash(track, actions.getActions());
//            long sum = 0;
//            int wins = 0;
//
//            for (int i = 0; i < NUMBER_OF_EPISODES; i++) {
//                double randomPrint = Math.random();
//                // <Episode>
//                Episode episode = new Episode();
//
//                State beginState;
//                State currentState;
//                do {
//                    // Get a random starting position and action
//                    Action randomAction = actions.getRandomStartingAction();
//
//                    beginState = new State(0, 0, track.getRandomStartingPosition());
//
//                    // Adding first pair to the episode
//                    episode.addPair(new StateActionHistory(new Pair(beginState, randomAction)));
//                    currentState = track.move(beginState, randomAction);
//
//                } while (currentState.getCell().getY() == beginState.getCell().getY());
//
//                int numberOfIteration = 0;
//                while (currentState.getCell().getSymbol() != track.EndPos && numberOfIteration <= MAX_ITERATIONS) {
//                    Action action; // our next action
//                    boolean crashed;
//
//                    do {
//                        crashed = false;
//                        double randomExplore = Math.random();
//                        if (randomExplore < 0.1) { // 10 % of the time we explore
//                            action = actions.getRandomAction(); // get random action
//                        } else {
//                            // Get the best action to take in the current state
//                            action = history.getBestAction(currentState, actions);
//                        }
//
//                        State lastState = currentState;
//                        currentState = track.move(currentState, action);
//
//                        if (lastState.getCell().getSymbol() == Track.OnTrack
//                                && action.getVelocity_right() != 0 && action.getVelocity_up() != 0) {
//                            if (currentState.getCell().getX() == lastState.getCell().getX() &&
//                                    currentState.getCell().getY() == lastState.getCell().getY()) {
//                                episode.updateReward(OFF_REWARD);
//                                crashed = true;
//                                numberOfIteration++;
//                                episode.addPair(new StateActionHistory(new Pair(currentState, action)));
//                            }
//                        }
//                    } while (crashed);
//
//                    // ARNAR
//                    episode.updateReward(ON_REWARD);
//                    episode.addPair(new StateActionHistory(new Pair(currentState, action)));
//
//                    // Only slide if we are moving.
//                    if (currentState.getVelocity_Right() != 0 && currentState.getVelocity_Up() != 0) {
//                        boolean didSlide = false;
//                        double randomSlide = Math.random();
//                        Cell slideCell = null;
//                        if (randomSlide < 0.25) { // Slide up 25 % of the time
//                            didSlide = true;
//                            slideCell = track.getCell(currentState.getCell().getX(),
//                                    currentState.getCell().getY() - 1);
//
//                        } else if (randomSlide > 0.75) { // Slide right 25 % of the time
//                            didSlide = true;
//                            slideCell = track.getCell(currentState.getCell().getX() + 1,
//                                    currentState.getCell().getY());
//                        }
//
//                        // TODO : check if we slide over the finish line
//
//                        // check if we slide and we slide out of the track
//                        if (didSlide) {
//
//                            // slideCell = currentState.getCell();
//                            if (slideCell == null) { // Out off the track array
////                                currentState.setVelocity_Up(0);
////                                currentState.setVelocity_Right(0);
////                                episode.updateReward(OFF_REWARD);
//                                continue;
//                            } else if (slideCell.getSymbol() == Track.OffTrack) { // Out off the track
//                                currentState.setVelocity_Up(0);
//                                currentState.setVelocity_Right(0);
//                                episode.updateReward(OFF_REWARD);
//                                currentState.setCell(slideCell);
//                                episode.addPair(new StateActionHistory(new Pair(currentState, action)));
//                            }
//                        }
//                    }
//
//                    Cell currentCell = currentState.getCell();
//                    if (currentCell != null) {
//                        episode.updateReward(currentCell.getReward());
//                    } else {
//                        System.out.println("ERROR: READING SYMBOL");
//                    }
//
//                    // Add action and current state to the list
//                    episode.addPair(new StateActionHistory(new Pair(currentState, action)));
//                    numberOfIteration++;
//
//                    if (currentCell.getSymbol() == Track.EndPos) {
//                        wins++;
//                    }
//
//                    if (currentCell.getSymbol() == Track.EndPos && randomPrint <= 0.0001) {
//                        System.out.print("won in : " + numberOfIteration + " iterations, ");
//                    }
//                }
//                // </Episode>
//
//                // Add the reward we got for the episode to each of the states
//                int reward_sum = episode.getReward();
//                for (StateActionHistory steps : episode.getStateActionHistorys()) {
//                    history.updateReward(steps.getPair(), reward_sum);
//                    reward_sum -= steps.getPair().getState().getCell().getReward();
//                }
////                if (reward_sum > 1) {
////                    System.out.println("ALERT REWARD SUM NOT 0");
////                    System.exit(1);
////                }
//                sum += episode.getReward();
//                if (randomPrint <= 0.0001) {
//
//                    System.out.println(" Round:" + i + " reward " + episode.getReward() +  " avg : "
//                            + sum / (double) i + " wins : " + wins);
////                    history.Print();
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
